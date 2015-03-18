package com.hyq.widget;


import java.util.Locale;
import com.hyqapp.R;
import android.animation.Animator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

//选项卡
@SuppressLint("ClickableViewAccessibility")
public class Tab implements View.OnTouchListener {
	 private final static int REVEAL_DURATION = 400;//波纹出现时间
	 private final static int HIDE_DURATION = 500;//波纹消失时间
	private View completeView;// 显示界面
	private ImageView iconIV;// 图标
	private TextView textTV;// 文字
	private RevealColorView background;// 背景效果
	private ImageView selectorIV;// 选中下标
	//
	private Resources res;
	private TabListener tabListener;
	//
	private int textColor;// 字体颜色
	private int iconColor;// 图标颜色
	private int primaryColor;// 主色
	private int accentColor;// 强调色
	//
	private boolean isSelected;// 选中
	private int position;// 标识
	private boolean hasIcon;// 有图标
	private float density;// 分辨率

	private Point lastTouchedPoint;//最后触摸点

	public Tab(Context context, boolean hasIcons) {
		this.hasIcon = hasIcons;
		res = context.getResources();
		density = res.getDisplayMetrics().density;

		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			if (!hasIcon) {
				completeView = LayoutInflater.from(context).inflate(
						R.layout.widget_tab_text, null);

				textTV = (TextView) completeView.findViewById(R.id.textTV);
			} else {
				completeView = LayoutInflater.from(context).inflate(
						R.layout.widget_tab_icon, null);

				iconIV = (ImageView) completeView.findViewById(R.id.iconIV);
			}

			selectorIV = (ImageView) completeView.findViewById(R.id.selectorIV);
		} else {
			if (!hasIcon) {
				// if there is no icon
				completeView = LayoutInflater.from(context).inflate(
						R.layout.widget_tab_text_effect, null);

				textTV = (TextView) completeView.findViewById(R.id.textTV);
			} else {
				// with icon
				completeView = LayoutInflater.from(context).inflate(
						R.layout.widget_tab_icon_effict, null);

				iconIV = (ImageView) completeView.findViewById(R.id.iconIV);
			}

			background = (RevealColorView) completeView
					.findViewById(R.id.reveal);
			selectorIV = (ImageView) completeView.findViewById(R.id.selectorIV);

		}
		// set the listener
		completeView.setOnTouchListener(this);

		isSelected = false;
		textColor = Color.WHITE;
		iconColor = Color.WHITE;
	}

	public void setPrimaryColor(int color) {
		this.primaryColor = color;

		if (rippleAble()) {
			background.setBackgroundColor(color);
		} else {
			completeView.setBackgroundColor(color);
		}

	}

	// 支持波纹
	private boolean rippleAble() {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			return false;
		} else {
			return true;
		}

	}

	public void setAccentColor(int color) {
		this.accentColor = color;
		this.textColor = color;
		this.iconColor = color;
	}

	public void setTextColor(int color) {
		textColor = color;
		if (textTV != null) {
			textTV.setTextColor(color);
		}

	}

	public void setIconColor(int color) {
		this.iconColor = color;
		if (this.iconIV != null)
			this.iconIV.setColorFilter(color);// TODO
	}

	public void activateTab() {
		// set full color text
		if (textTV != null)
			this.textTV.setTextColor(textColor);
		// set 100% alpha to icon
		if (iconIV != null)
			setIconAlpha(0xFF);

		// set accent color to selector view
		this.selectorIV.setBackgroundColor(accentColor);

		isSelected = true;
	}

	public void disableTab() {
		// set 60% alpha to text color
		if (textTV != null)
			this.textTV.setTextColor(Color.argb(0x99, Color.red(textColor),
					Color.green(textColor), Color.blue(textColor)));
		// set 60% alpha to icon
		if (iconIV != null)
			setIconAlpha(0x99);

		// set transparent the selector view
		this.selectorIV.setBackgroundColor(res
				.getColor(android.R.color.transparent));

		isSelected = false;

		if (tabListener != null)
			tabListener.onTabUnselected(this);

	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private void setIconAlpha(int alpha) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			this.iconIV.setImageAlpha(alpha);
		}else{
			this.iconIV.setColorFilter(Color.argb(alpha, Color.red(this.iconColor),
					Color.green(this.iconColor), Color.blue(this.iconColor)));
		}
	}

	public View getView() {
		return completeView;
	}

	public float getTabMinWidth() {
		if (hasIcon) {
			return (int) (density * 24);
		} else {
			return getTextLenght();
		}
	}

	private int getTextLenght() {
		String textString = textTV.getText().toString();
		Rect bounds = new Rect();
		Paint textPaint = textTV.getPaint();
		textPaint.getTextBounds(textString, 0, textString.length(), bounds);
		return bounds.width();
	}

	public boolean isSelected() {
		return isSelected;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public TabListener getTabListener() {
		return tabListener;
	}

	public Tab setTabListener(TabListener tabListener) {
		this.tabListener = tabListener;
		return this;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public boolean onTouch(View view, MotionEvent event) {
		lastTouchedPoint = new Point();
		lastTouchedPoint.x = (int) event.getX();
        lastTouchedPoint.y = (int) event.getY();

        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            if(!rippleAble()) {
                completeView.setBackgroundColor(Color.argb(0x80, Color.red(accentColor), Color.green(accentColor), Color.blue(accentColor)));
            }
            return true;
        }

        if(event.getAction() == MotionEvent.ACTION_CANCEL) {
            if(!rippleAble()) {
                completeView.setBackgroundColor(primaryColor);
            }
            return true;
        }

        // new effects
        if(event.getAction() == MotionEvent.ACTION_UP) {

            if(!rippleAble()) {
                completeView.setBackgroundColor(primaryColor);
            } else {
            	 Animator.AnimatorListener listener=new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        background.reveal(lastTouchedPoint.x, lastTouchedPoint.y, primaryColor, 0, HIDE_DURATION, null);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                };
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                	this.background.reveal(lastTouchedPoint.x, lastTouchedPoint.y, Color.argb(0x80, Color.red(accentColor), Color.green(accentColor), Color.blue(accentColor)), 0, REVEAL_DURATION, null);
				}else{
					this.background.reveal(lastTouchedPoint.x, lastTouchedPoint.y, Color.argb(0x80, Color.red(accentColor), Color.green(accentColor), Color.blue(accentColor)), 0, REVEAL_DURATION, listener);
				}
            }

            // set the click
            if(tabListener != null) {

                if(isSelected) {
                    // if the tab is active when the user click on it it will be reselect
                	tabListener.onTabReselected(this);
                }
                else {
                	tabListener.onTabSelected(this);
                }
            }
            // if the tab is not activated, it will be active
            if(!isSelected)
                this.activateTab();

            return true;
        }

		return false;
	}

	public Tab setText(CharSequence text) {
        if(hasIcon)
            throw new RuntimeException("uses icons instead text");

		this.textTV.setText(text.toString().toUpperCase(Locale.US));
        return this;
	}
	
	public Tab setIcon(Drawable icon) {
        if(!hasIcon)
            throw new RuntimeException("uses text instead icons");

		this.iconIV.setImageDrawable(icon);
		this.setIconColor(this.iconColor);
		return this;
	}
	public interface TabListener {
		/**选择*/
		public void onTabSelected(Tab tab);
		
		/**重选*/
		public void onTabReselected(Tab tab);
		
		/**反选*/
		public void onTabUnselected(Tab tab);
	}
}
