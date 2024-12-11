package zj.dzh.music_list.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import zj.dzh.music_list.Bean.Singer;
import zj.dzh.music_list.R;

public class SingerGridAdapter extends BaseAdapter {
    private Context context;
    private List<Singer> mDatas;

    public SingerGridAdapter(Context context, List<Singer> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }


    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler viewHodler = null;
        if (convertView == null) {
            //将item布局转换成view视图
            convertView = LayoutInflater.from(context).inflate(R.layout.item_singergrid,null);
            viewHodler = new ViewHodler(convertView);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }
        // 获取指定位置的数据
        Singer singer = mDatas.get(position);
        viewHodler.iv.setImageResource(singer.getImgId());
        viewHodler.tv.setText(singer.getName());
        return convertView;
    }

    class ViewHodler {
        ImageView iv; //子项的图片框
        TextView tv;  //子项的文本框
        public ViewHodler(View view) {
            iv = view.findViewById(R.id.item_grid_iv);
            tv = view.findViewById(R.id.item_grid_tv);
        }
    }
}
