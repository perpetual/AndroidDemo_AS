package com.example.androiddemo.utils;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.ClipboardManager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.text.style.UnderlineSpan;

/**
 * <pre>
 * Copyright (C) 1998-2013 TENCENT Inc.All Rights Reserved.<br>
 *
 * FileName: TextUtil.java<br>
 *
 * Description�? 文本工具�?<br>
 * 
 * History�?<br>
 * 
 * User				Date			Info		Reason<br>
 * yongzhiguo		2013-3-28		Create		<br>
 * yourName			yourDate		Modify		<br>
 * </pre>
 */
public class TextUtil {
   
	/**
	 * 文本高亮处理
	 * 
	 * @param s
	 *            要高亮处理的字符
	 * @param startPos
	 *            �?始位�?
	 * @param len
	 *            长度
	 * @param prefix
	 *            高亮前缀，HTML代码
	 * @param subfix
	 *            高亮后缀，HTML代码
	 * @return 加入前缀和后�?的字符串
	 */
	public static CharSequence foregroundColorForText(CharSequence s, int startPos, int len,
			int color) {

		SpannableStringBuilder buf = new SpannableStringBuilder(s);
		if (startPos + len <= s.length() && startPos >= 0 ) {
		ForegroundColorSpan span = new ForegroundColorSpan(color);

			buf.setSpan(span, startPos, startPos + len,
					Spannable.SPAN_INCLUSIVE_EXCLUSIVE );
		}
		return buf;
	}
	
	/**
	 * 图片替换文本
	 * @param s
	 * @param startPos
	 * @param len 
	 * @param ResId 图片资源
	 * @param verticalAlignment 方位(如：DynamicDrawableSpan.ALIGN_BOTTOM,DynamicDrawableSpan.ALIGN_BASELINE)
	 * @return
	 *
	 * @author yongzhiguo in 2013-3-28
	 * @modify by yourName in yourDate for yourReason
	 */
	public static CharSequence imageForText(CharSequence s, int startPos, int len, int ResId,
			int verticalAlignment) {
		
		SpannableStringBuilder buf = new SpannableStringBuilder(s);
		if (startPos + len <= s.length() && startPos >= 0 ) {
			Drawable drawable = AndroidDemoUtil.APPLICATION_CONTEXT.getResources()
					.getDrawable(ResId);
			if (drawable != null) {
				ImageSpan span = new ImageSpan(drawable, verticalAlignment);
				buf.setSpan(span, startPos, startPos + len, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
			}
		}
		return buf;
	}
	
	/**
	 * 文本添加下划�?
	 * @param s
	 * @param startPos
	 * @param endPos
	 * @return
	 *
	 * @author yongzhiguo in 2013-3-28
	 * @modify by yourName in yourDate for yourReason
	 */
	public static CharSequence underlineForText(CharSequence s, int startPos, int endPos){
		 SpannableStringBuilder buf=new SpannableStringBuilder(s);  
		 if (endPos >= startPos && endPos <= s.length() && startPos >= 0 ) {
		    UnderlineSpan span=new UnderlineSpan();  
		    buf.setSpan(span, startPos, endPos, Spannable.SPAN_INCLUSIVE_EXCLUSIVE );  
		 }
		 return buf;
	}
	
	/**
	 * 字体风格设置
	 * @param s
	 * @param startPos
	 * @param len
	 * @param style  (如：android.graphics.Typeface.ITALIC)
	 * @return
	 *
	 * @author yongzhiguo in 2013-3-28
	 * @modify by yourName in yourDate for yourReason
	 */
	public static CharSequence characterStyleForText(CharSequence s, int startPos, int len,int style){
		 SpannableStringBuilder buf=new SpannableStringBuilder(s);  
		 if (startPos + len <= s.length() && startPos >= 0 ) {
			 CharacterStyle span=new StyleSpan(style);  
		     buf.setSpan(span, startPos, len, Spannable.SPAN_INCLUSIVE_EXCLUSIVE );  
		 }
		 return buf;
	}
	
	/**
	 * 字体设置
	 * @param s
	 * @param startPos
	 * @param len
	 * @param typeface (如："monospace", "serif", and "sans-serif")
	 * @return
	 *
	 * @author yongzhiguo in 2013-3-28
	 * @modify by yourName in yourDate for yourReason
	 */
	public static CharSequence characterTypefaceForText(CharSequence s, int startPos, int len,String typeface){
		 SpannableStringBuilder buf=new SpannableStringBuilder(s);  
		 if (startPos + len <= s.length() && startPos >= 0 ) {
			 CharacterStyle span=new TypefaceSpan(typeface);  
		     buf.setSpan(span, startPos, len, Spannable.SPAN_INCLUSIVE_EXCLUSIVE );  
		 }
		 return buf;
	}
	
	/**
	 * 设置背景�?
	 * @param s
	 * @param startPos
	 * @param len
	 * @param color
	 * @return
	 *
	 * @author yongzhiguo in 2013-3-28
	 * @modify by yourName in yourDate for yourReason
	 */
	public static CharSequence backgroundColorForText(CharSequence s, int startPos, int len,int color){
		 SpannableStringBuilder buf=new SpannableStringBuilder(s);  
		 if (startPos + len <= s.length() && startPos >= 0 ) {
			 CharacterStyle span=new BackgroundColorSpan(color);  
		     buf.setSpan(span, startPos, len, Spannable.SPAN_INCLUSIVE_EXCLUSIVE );  
		 }
		 return buf;
	}
	
	/**
	 * 缩放字体
	 * @param s
	 * @param startPos
	 * @param len
	 * @param proportion 缩放倍数(默认�?1,设置后就是原来的乘以proportion，大�?1时放�?(zoon in)，小于时缩小(zoom out))
	 * @return
	 *
	 * @author yongzhiguo in 2013-3-28
	 * @modify by yourName in yourDate for yourReason
	 */
	public static CharSequence scaleXForText(CharSequence s, int startPos, int len,float proportion){
		 SpannableStringBuilder buf=new SpannableStringBuilder(s);  
		 if (startPos + len <= s.length() && startPos >= 0 ) {
			 CharacterStyle span=new ScaleXSpan(proportion);  
		     buf.setSpan(span, startPos, len, Spannable.SPAN_INCLUSIVE_EXCLUSIVE );  
		 }
		 return buf;
	} 
	
	/**
	 * 会有�?条线从中间穿过�?�中的字
	 * @param s
	 * @param startPos
	 * @param len
	 * @return
	 *
	 * @author yongzhiguo in 2013-3-28
	 * @modify by yourName in yourDate for yourReason
	 */
	public static CharSequence strikethroughForText(CharSequence s, int startPos, int len){
		 SpannableStringBuilder buf=new SpannableStringBuilder(s);  
		 if (startPos + len <= s.length() && startPos >= 0 ) {
			 CharacterStyle span=new StrikethroughSpan();  
		     buf.setSpan(span, startPos, len, Spannable.SPAN_INCLUSIVE_EXCLUSIVE );  
		 }
		 return buf;
	} 
	
	/**
	 * 复制到剪切板
	 *
	 * @param text
	 */
	public static void setClipBoard(CharSequence text) {
		try {
			//某些rom上有Bug，需要保护一�?
			ClipboardManager clipboardManager = (ClipboardManager) AndroidDemoUtil.APPLICATION_CONTEXT
					.getSystemService(Context.CLIPBOARD_SERVICE);
			clipboardManager.setText(text);
		} catch (Exception e) {
			LogUtil.w("TextUtils", "setClipBoard:" + e.getMessage());
		}
	}
    
	/**
	 * 获取剪切板文�?
	 * @return
	 */
	public static CharSequence getClipBoard(){
		ClipboardManager clipboardManager = (ClipboardManager) AndroidDemoUtil.APPLICATION_CONTEXT
		.getSystemService(Context.CLIPBOARD_SERVICE);
		return clipboardManager.getText();
	} 
	
	/**
	 * 判断字符串是否为空，如果为空则返回空�?
	 * 
	 * @param s
	 *            字符�?
	 * @return
	 */
	public static String emptyIfNull(String s) {
		return s == null ? "" : s;
	}
	
	public static String toHex(int decimal) {
		StringBuilder sb = new StringBuilder("0x");
		sb.append(Integer.toHexString(decimal));
		return sb.toString();
	}
	
	/**
	 * 把windows的文本转化为linux的文本格式，主要是换行符的处�?; windows的换行符\r\n,而linux的换行符为\n
	 * 
	 * @param text
	 * @return
	 */
	public static CharSequence toUnixDelimiters(CharSequence text) {
		if (text != null) {
			StringBuilder sb = new StringBuilder();
			int len = text.length();
			int i = 0;
			int step = 1;
			while (i < len) {
				char c = text.charAt(i);
				if (c == '\r') {
					if (i < len - 1) {
						if (text.charAt(i + 1) == '\n') {
							step = 2;
						}
					}
					sb.append('\n');
				} else if (c == '\n') {
					if (i < len - 1) {
						if (text.charAt(i + 1) == '\r') {
							step = 2;
						}
					}
					sb.append('\n');
				} else if (c == (char) 0x2029 || c == (char) 0x0c) {
					sb.append('\n');
				} else {
					sb.append(c);
				}

				i += step;
				step = 1;
			}
			return sb.toString();
		}
		return text;
	}
	
	public static String toEndWithEllipsis(final String str, final int maxLength) {
		if (null == str || 0 == str.trim().length()) {
			return str;
		}
		final char suffix = 0x2026; // Ellipsis char, ... / &#8230; / 0x2026
		final int suffixLen = 1;

		final StringBuilder sb = new StringBuilder();
		final char[] chr = str.trim().toCharArray();
		int len = 0;
		for (int i = 0; i < chr.length; i++) {
			if (chr[i] >= 0xa1) {
				// Chinese char
				len += 2;
			} else {
				len++;
			}
		}

		if (len <= maxLength) {
			return str;
		}

		len = 0;
		for (int i = 0; i < chr.length; i++) {
			if (chr[i] >= 0xa1) {
				len += 2;
			} else {
				len++;
			}
			if (len + suffixLen > maxLength) {
				break;
			} else {
				sb.append(chr[i]);
			}
		}
		sb.append(suffix);
		
		return sb.toString();
	}

	public static CharSequence replaceRt2Space(CharSequence text) {
		if (text != null) {
			StringBuilder sb = new StringBuilder();
			int len = text.length();
			int i = 0;
			int step = 1;
			while (i < len) {
				char c = text.charAt(i);
				if (c == '\r') {
					if (i < len - 1) {
						if (text.charAt(i + 1) == '\n') {
							step = 2;
						}
					}
					sb.append(' ');
				} else if (c == '\n') {
					if (i < len - 1) {
						if (text.charAt(i + 1) == '\r') {
							step = 2;
						}
					}
					sb.append(' ');
				} else if (c == (char) 0x2029 || c == (char) 0x0c) {
					sb.append(' ');
				} else {
					sb.append(c);
				}

				i += step;
				step = 1;
			}
			return sb.toString();
		}
		return text;
	} 
	
	public static float getTextPixels(float textFont, String sourceText) {
		
		if ( textFont < 0 || sourceText == null || sourceText.length() == 0 ) {
			
			LogUtil.w("TextUtils", "getTextPixels textFont=" + textFont + " sourceText=" + sourceText);
			return 0;
		}
		
		Paint p = new Paint();
		p.setTextSize(textFont);
		float dipWidth = p.measureText(sourceText);

		return AndroidDemoUtil.dip2px(dipWidth);
	}
	
	public static boolean isValidMobileNum(String phoneNum) {
		if(phoneNum.length() < 11){
	        return false;
	    }
		phoneNum = phoneNum.replace(" ", "").replace("-", "").replace("(", "").replace(")", "");
		phoneNum = phoneNum.replaceFirst("^\\+?(00)?86", "");
	    if(phoneNum.length() != 11){
	    	return false;
	    }
		String regex = "^(1(([35][0-9])|(47)|[8][012356789]))\\d{8}$";
	    if(phoneNum.matches(regex)){
	        return true;
	    }
	    return false;
	}
	
}

