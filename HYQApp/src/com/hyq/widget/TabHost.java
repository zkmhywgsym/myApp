package com.hyq.widget;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hyqapp.R;

//主页下面选项 A Toolbar that contains multiple tabs
public class TabHost extends RelativeLayout implements View.OnClickListener {
	private final int LEFT=100011,RIGHT=100012;
	private boolean hasIcons;// 是否有图标
	private int primaryColor;// 主色
	private int accentColor;// 强调色
	private int iconColor;// 图标颜色
	private int textColor;// 文字颜色
	private int tabSelected;// 选中项
	private boolean isTablet;//是否是平板
	private float density;//分辨率
	private boolean scrollable;//是否滚动
	private List<Tab> tabs;//选项卡集合
	private HorizontalScrollView scrollView;//选项卡界面外
	private LinearLayout layout;//选项卡界面内

	public TabHost(Context context) {
		this(context, null);
	}

	public TabHost(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public TabHost(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		scrollView = new HorizontalScrollView(context);
		scrollView.setOverScrollMode(HorizontalScrollView.OVER_SCROLL_NEVER);
		scrollView.setHorizontalScrollBarEnabled(false);
		layout=new LinearLayout(context);
		scrollView.addView(layout);

		// get attributes
		if (attrs != null) {
			TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
					R.styleable.TabHost, 0, 0);

			try {
				// custom attributes
				hasIcons = a.getBoolean(R.styleable.TabHost_hasIcons, false);

				primaryColor = a.getColor(R.styleable.TabHost_primaryColor,
						Color.parseColor("#009688"));
				accentColor = a.getColor(R.styleable.TabHost_accentColor,
						Color.parseColor("#00b0ff"));
				iconColor = a.getColor(R.styleable.TabHost_iconColor,
						Color.WHITE);
				textColor = a.getColor(R.styleable.TabHost_textColor,
						Color.WHITE);
			} finally {
				a.recycle();
			}
		} else {
			hasIcons = false;
		}

		this.isInEditMode();
		scrollable = false;
		isTablet = this.getResources().getBoolean(R.bool.isTablet);
		density = this.getResources().getDisplayMetrics().density;
		tabSelected = 0;

		// initialize tabs list
		tabs = new LinkedList<Tab>();

		// set background color
		super.setBackgroundColor(primaryColor);
	}
	//设置主要颜色
	public void setPrimaryColor(int color) {
		this.primaryColor = color;

        this.setBackgroundColor(primaryColor);

		for(Tab tab : tabs) {
			tab.setPrimaryColor(color);
		}
	}
	//设置强调色
	public void setAccentColor(int color) {
		this.accentColor = color;
		
		for(Tab tab : tabs) {
			tab.setAccentColor(color);
		}
	}
	//设置文字色
	public void setTextColor(int color) {
		this.textColor = color;
		
		for(Tab tab : tabs) {
			tab.setTextColor(color);
		}
	}
	//设置图标色
	public void setIconColor(int color) {
		this.iconColor = color;
		
		for(Tab tab : tabs) {
			tab.setIconColor(color);
		}
	}
	//添加新选项卡
	public void addTab(Tab tab) {
        // add properties to tab
        tab.setAccentColor(accentColor);
        tab.setPrimaryColor(primaryColor);
        tab.setTextColor(textColor);
        tab.setIconColor(iconColor);
        tab.setPosition(tabs.size());

        tabs.add(tab);

        // 在绘制选项卡前先切换成可滚动switch tabs to scrollable before its draw
        if(tabs.size() == 4 && !hasIcons) {
            scrollable = true;
        }
        if(tabs.size() == 6 && hasIcons) {
            scrollable = true;
        }
	}
	//创建新选项卡
	public Tab newTab(){
		return new Tab(getContext(),hasIcons);
	}
	//设置选项卡选中
	public void setSelectedTab(int position) {
		if(position < 0 || position > tabs.size()) {//参数非法
			throw new RuntimeException("Index overflow");
		} else {
			//指定选项卡选中，其它的不选 tab at position will select, other will deselect
			for(int i = 0; i < tabs.size(); i++) {
				Tab tab = tabs.get(i);
				
				if(i == position) {
					tab.activateTab();
				}
				else {
					tabs.get(i).disableTab();
				}
			}

            // move the tab if it is slidable
            if(scrollable) {
                scrollTo(position);
            }

            tabSelected = position;
		}
		
	}
	//滚动到当前位置
	private void scrollTo(int position) {
        int totalWidth = 0;//(int) ( 60 * density);
        for (int i = 0; i < position; i++) {
            int width = tabs.get(i).getView().getWidth();
            if(width == 0) {
                if(!isTablet)
                    width = (int) (tabs.get(i).getTabMinWidth() + (24 * density));
                else
                    width = (int) (tabs.get(i).getTabMinWidth() + (48 * density));
            }

            totalWidth += width;
        }
        scrollView.smoothScrollTo(totalWidth, 0);
    }
	@Override
	public void removeAllViews() {
		for(int i = 0; i<tabs.size();i++) {
			tabs.remove(i);
		}
		layout.removeAllViews();
        super.removeAllViews();
	}
	@Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(this.getWidth() != 0 && tabs.size() != 0)
            notifyDataSetChanged();
    }
	public void notifyDataSetChanged() {
        super.removeAllViews();
        layout.removeAllViews();


        if (!scrollable) { 
            int tabWidth = this.getWidth() / tabs.size();

            // set params for resizing tabs width
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(tabWidth, HorizontalScrollView.LayoutParams.MATCH_PARENT);
            for (Tab t : tabs) {
                layout.addView(t.getView(), params);
            }

        } else { 

        	LinearLayout.LayoutParams params;
            if(!isTablet) {//普通
                for (int i = 0; i < tabs.size(); i++) {
                    Tab tab = tabs.get(i);

                    int tabWidth = (int) (tab.getTabMinWidth() + (24 * density)); // 12dp + text/icon width + 12dp

                    if (i == 0) {
                        // first tab
                        View view = new View(layout.getContext());
                        view.setMinimumWidth((int) (60 * density));
                        layout.addView(view);
                    }
                    params = new LinearLayout.LayoutParams(tabWidth, HorizontalScrollView.LayoutParams.MATCH_PARENT);
                    layout.addView(tab.getView(), params);
                    if (i == tabs.size() - 1) {
                    	// last tab
                    	View view = new View(layout.getContext());
                    	view.setMinimumWidth((int) (60 * density));
                    	layout.addView(view);
                    }

                }
            }
            else {//平板
                for (int i = 0; i < tabs.size(); i++) {
                    Tab tab = tabs.get(i);

                    int tabWidth = (int) (tab.getTabMinWidth() + (48 * density)); // 24dp + text/icon width + 24dp

                    params = new LinearLayout.LayoutParams(tabWidth, HorizontalScrollView.LayoutParams.MATCH_PARENT);
                    layout.addView(tab.getView(), params);
                }
            }
        }

        ImageButton left,right;
        if (isTablet && scrollable) {
            // if device is a tablet and have scrollable tabs add right and left arrows
            Resources res = getResources();

            left = new ImageButton(this.getContext());
            left.setId(LEFT);
            left.setImageDrawable(res.getDrawable(R.drawable.left_arrow));
            left.setBackgroundColor(Color.TRANSPARENT);
            left.setOnClickListener(this);

            // set 56 dp width and 48 dp height
            RelativeLayout.LayoutParams paramsLeft = new LayoutParams((int)( 56 * density),(int) (48 * density));
            paramsLeft.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            paramsLeft.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            paramsLeft.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            this.addView(left,paramsLeft);

            right = new ImageButton(this.getContext());
            right.setId(RIGHT);
            right.setImageDrawable(res.getDrawable(R.drawable.right_arrow));
            right.setBackgroundColor(Color.TRANSPARENT);
            right.setOnClickListener(this);

            RelativeLayout.LayoutParams paramsRight = new LayoutParams((int)( 56 * density),(int) (48 * density));
            paramsRight.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            paramsRight.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            paramsRight.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            this.addView(right,paramsRight);

            RelativeLayout.LayoutParams paramsScroll = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            paramsScroll.addRule(RelativeLayout.LEFT_OF, RIGHT);
            paramsScroll.addRule(RelativeLayout.RIGHT_OF,LEFT);
            this.addView(scrollView,paramsScroll);
        }else {
            // if is not a tablet add only scrollable content
            RelativeLayout.LayoutParams paramsScroll = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            this.addView(scrollView,paramsScroll);
        }

        this.setSelectedTab(tabSelected);
    }
	//获取当前选项卡
	public Tab getCurrentTab() {
        for(Tab tab : tabs) {
            if (tab.isSelected())
                return tab;
        }

        return null;
    }
	@Override
	public void onClick(View view) {
		int currentPosition = this.getCurrentTab().getPosition();

        if (view.getId() == RIGHT && currentPosition < tabs.size() -1) {
            currentPosition++;

            // set next tab selected
            this.setSelectedTab(currentPosition);

            // change fragment
            tabs.get(currentPosition).getTabListener().onTabSelected(tabs.get(currentPosition));
            return;
        }

        if(view.getId() == LEFT && currentPosition > 0) {
            currentPosition--;

            // set previous tab selected
            this.setSelectedTab(currentPosition);
            // change fragment
            tabs.get(currentPosition).getTabListener().onTabSelected(tabs.get(currentPosition));
            return;
        }

	}
}
