package com.rock.android.tagselectorview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.rock.android.tagselector.LogUtil;
import com.rock.android.tagselector.SimpleAdapter;
import com.rock.android.tagselector.interfaces.ITagSelectorTabView;
import com.rock.android.tagselector.model.DataBean;
import com.rock.android.tagselector.model.Tags;
import com.rock.android.tagselector.views.TagSelectView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private TagSelectView tagSelectView;
    private List<Tags> dataList;
    private TextView firstTabTv;
    private SimpleAdapter tagSelectorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tagSelectView = (TagSelectView) findViewById(R.id.tagSelectView);

        tagSelectView.setOnTagViewStatusChangedListener(new TagSelectView.OnTagViewStatusChangedListener() {
            @Override
            public void onOpened(ITagSelectorTabView openedView) {
                TextView textView = openedView.getTextView();
                if(textView == firstTabTv){
                    textView.setTextColor(Color.YELLOW);
                }else{
                    textView.setTextColor(Color.BLUE);
                }
            }

            @Override
            public void onClosed(ITagSelectorTabView closedView) {
                TextView textView = closedView.getTextView();
                if(textView == firstTabTv){
                    textView.setTextColor(Color.CYAN);
                }else{
                    textView.setTextColor(Color.BLACK);
                }

            }
        });

        tagSelectView.setOnTagSelectedListener(new TagSelectView.OnTagSelectedListener() {
            @Override
            public void onTagSelected(int selectorListPosition, int tabPosition) {
                LogUtil.e("onTagSelected", "selected==" + selectorListPosition + "===tab===" + tabPosition);

                DataBean dataBean = dataList.get(tabPosition).tags.get(selectorListPosition);
                MyDataBean d = (MyDataBean) dataBean;
                LogUtil.e("onTagSelected", "data name===" + d.name);

                DataBean dataBean1 = (DataBean) tagSelectorAdapter.getItem(tabPosition,selectorListPosition);
                        //(DataBean) tagSelectView.getTabView(tabPosition).getTagSelectorView().getCurrentItem(selectorListPosition);
                LogUtil.e("onTagSelected", "data1 name===" + dataBean1.name);
                //do something like request the network

            }
        });
        initData();

    }

    private void initData() {
        dataList = new ArrayList<>();

        int count = 2;
        List<DataBean> dataBeanList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            dataBeanList.add(new MyDataBean("item A" + i));
        }

        dataList.add(new Tags()
                .setTags(dataBeanList));



        List<DataBean> dataBeanList1 = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            dataBeanList1.add(new MyDataBean("item B" + i));
        }

        dataList.add(new Tags()
                .setTags(dataBeanList1));


        int countc = 10;
        List<DataBean> dataBeanList2 = new ArrayList<>();
        for (int i = 0; i < countc; i++) {
            dataBeanList2.add(new MyDataBean("item C" + i));
        }

        dataList.add(new Tags()
                .setTags(dataBeanList2));

        tagSelectorAdapter = new SimpleAdapter(dataList, this);
        tagSelectView.setAdapter(tagSelectorAdapter);
        tagSelectView.setWrapperHeight(FrameLayout.LayoutParams.MATCH_PARENT);

        firstTabTv = (TextView) findViewById(R.id.theTextView);

    }

    public void onClickAddData(View v){
        //insert a data
        tagSelectView.getTabView(1).getTagSelectorView().insert(0, new MyDataBean("im new"));
        tagSelectView.getTabView(1).getTagSelectorView().refresh();
    }
}
