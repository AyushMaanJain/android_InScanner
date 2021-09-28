package com.sm.test_scanner.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.text.InputType;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import com.sm.test_scanner.R;
import com.sm.test_scanner.data.TabeModel;

import java.util.ArrayList;

public class TableView extends HorizontalScrollView {
    ArrayList<TextView> al=new ArrayList<>();
    Context context;
    TableLayout tableLayout;
    public TableView(Context context) {
        super(context);
        this.context=context;
        String columns[]=new String[]{"ID","PRODUCT CATAGORY","SAMPLE TYPE","DESIGNER","CATAGORY","PRODUCT TYPE","DESIGN HEAD","SEASON","QUANTITY"};
        int width=context.getResources().getDisplayMetrics().widthPixels;
        this.setPadding(5,15,5,35);
        tableLayout=new TableLayout(context);
        LinearLayout table=new LinearLayout(context);
        table.setOrientation(LinearLayout.VERTICAL);

        TableRow tr1=new TableRow(context);
        tr1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.FILL_PARENT));



        for(String s:columns){
            TextView tv=new TextView(context);
            tv.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
            tv.setText(s);
            tv.setPadding(25,10,25,10);
            tv.setTextSize(18);
            tv.setTextAlignment(TEXT_ALIGNMENT_CENTER);
            tv.setBackgroundResource(R.drawable.table_head);
            tv.setTextColor(0xff000000);
            tv.setTypeface(Typeface.DEFAULT_BOLD);

            tr1.addView(tv);


            al.add(tv);

        }
        tableLayout.addView(tr1);
        //ViewTreeObserver observer = tableLayout.getViewTreeObserver();
       /* observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int w = tableLayout.getWidth();
                if(w<(width-10)) {
                    float ratio=(float)(width-10)/(float)w;
                    for(TextView tc:al){
                        int s1=tc.getWidth();
                        int s2=(int) (s1*ratio);
                        tc.setWidth(s2);
                    }

                }
                tableLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });*/
        this.addView(tableLayout);

    }
    public void addRow(TabeModel tm){
        try {
            TableRow tr2 = new TableRow(context);
            tr2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.FILL_PARENT));
            TextView id = generateCell(tm.getId() + "");
            TextView pc = generateCell(tm.getProductCatagory());
            TextView sam = generateCell(tm.getSampleType());
            TextView des = generateCell(tm.getDesigner());
            TextView cat = generateCell(tm.getCatagory());
            TextView pt = generateCell(tm.getProductType());
            TextView dh = generateCell(tm.getDesignHead());
            TextView sez = generateCell(tm.getSeason());
            EditText qty = generateEditableCell(tm.getQty() + "");
            tr2.addView(id);
            tr2.addView(pc);
            tr2.addView(sam);
            tr2.addView(des);
            tr2.addView(cat);
            tr2.addView(pt);
            tr2.addView(dh);
            tr2.addView(sez);
            tr2.addView(qty);
            tableLayout.addView(tr2);
        }catch (Exception e){
            Log.e("Error",e.toString());
        }
    }
      public TextView generateCell(String data){
          TextView tv2=new TextView(context);
          tv2.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
          if(data==null){tv2.setText("------");}
          else{tv2.setText(data);}
          tv2.setPadding(10,10,10,10);
          tv2.setTextSize(13);
          tv2.setTextAlignment(TEXT_ALIGNMENT_CENTER);
          tv2.setBackgroundResource(R.drawable.table_cell);
          tv2.setTextColor(0xff000000);
          return tv2;
      }
      public EditText generateEditableCell(String data){
          EditText tv2=new EditText(context);
          tv2.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
          if(data==null){tv2.setText("------");}
          else{tv2.setText(data);}
          tv2.setPadding(10,10,10,10);
          tv2.setTextSize(13);
          tv2.setInputType(InputType.TYPE_CLASS_NUMBER);
          tv2.setTextAlignment(TEXT_ALIGNMENT_CENTER);
          tv2.setBackgroundResource(R.drawable.table_cell);
          tv2.setTextColor(0xff000000);
          return tv2;
      }

}
