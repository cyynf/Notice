# Android_Notice
安卓上下滚动通知
``` java

notice.setData(new NoticeView.ShowText() {
    @Override
    public String getText(int position) {
        return list.get(position);
    }

    @Override
    public int getSize() {
        return list.size();
    }
});

notice.start(3000, 3000);

notice.setOnItemClickListener(new NoticeView.OnItemClickListener() {
    @Override
    public void click(int position) {
       
    }
});

```
