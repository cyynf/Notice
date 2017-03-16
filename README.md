# Android_Notice
安卓上下滚动通知
``` java

notice.setData(new NoticeView.ShowText() {
    @Override
    public String getText(int position) {
        return entry.getAppData().getArticles().get(position).getFTitle();
    }

    @Override
    public int getSize() {
        return entry.getAppData().getArticles().size();
    }
});

notice.start(3000, 3000);

notice.setOnItemClickListener(new NoticeView.OnItemClickListener() {
    @Override
    public void click(int position) {
        HomeEntry.AppDataBean.ArticlesBean articles = entry.getAppData().getArticles().get(position);
        Intent intent = new Intent(getActivity(), LocalWebViewActivity.class);
        intent.putExtra("title", articles.getFTitle());
        intent.putExtra("data", articles.getFContents());
        ((MainActivity) getActivity()).startActivityWithAnim(intent);
    }
});

```
