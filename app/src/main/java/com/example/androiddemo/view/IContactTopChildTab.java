package com.example.androiddemo.view;

/**
 * ��ϵ�˶���Tab�Ľӿ�
 * @author kross(krossford@foxmail.com)
 * @update 2014-12-5 18:05:48 created
 * */
public interface IContactTopChildTab {
    
    /**
     * ���ť
     * @param isActive true��ʾ����״̬��false��ʾ�Ǽ���״̬
     * */
    public void active(boolean isActive);
    
    
    public void showRedPoint();
    /**
     * ����С���
     * */
    public void hideRedPoint();
    /**
     * ��ʾС��㣬�������Ĳ��� <= 0�����Զ�����С���
     * @param number С����е�����
     * */
    public void showRedPointNum(int number);
    
    public void hideRedPointNum();
}
