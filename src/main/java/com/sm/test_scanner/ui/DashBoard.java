package com.sm.test_scanner.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.sm.test_scanner.R;
import com.sm.test_scanner.data.TabeModel;

public class DashBoard extends LinearLayout {

    int location=0;
    EditText QRText;
    Button addButton,scanButton;
    TableView tb;
    OnClickListener qrLis=new OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };
    OnClickListener add=new OnClickListener() {
        @Override
        public void onClick(View view) {
            newRow();
        }
    };

    public void newRow(){
        tb.addRow(new TabeModel(5));
    }

    public DashBoard(Context context) {
        super(context);

        this.setOrientation(VERTICAL);
        int width=context.getResources().getDisplayMetrics().widthPixels;
        int height=context.getResources().getDisplayMetrics().heightPixels;

        LinearLayout respLayout=new LinearLayout(context);
        respLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        LayoutParams lpp;
        if(width>height){
            respLayout.setOrientation(HORIZONTAL);
            lpp=new LayoutParams(width/2, ViewGroup.LayoutParams.WRAP_CONTENT);
        }else{
            respLayout.setOrientation(VERTICAL);
            lpp=new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        }
        LinearLayout loc=new LinearLayout(context);
        loc.setOrientation(VERTICAL);
        loc.setPadding(20,20,20,20);
        loc.setLayoutParams(lpp);
        {
            TextView heading = new TextView(context);
            heading.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            heading.setText("Location");
            heading.setTextColor(0xff000000);
            heading.setTypeface(Typeface.DEFAULT_BOLD);
            heading.setPadding(10,10,10,10);
            heading.setTextSize(20);
            loc.addView(heading);

            TextView dropDown = new TextView(context);
            dropDown.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            dropDown.setText("Select Location");
            dropDown.setTextColor(0xff000000);
            dropDown.setTextSize(15);
            dropDown.setGravity(Gravity.CENTER_VERTICAL);
            dropDown.setPadding(20,20,20,20);
            dropDown.setBackgroundResource(R.drawable.dropdown_bg);
            loc.addView(dropDown);
            dropDown.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    PopupMenu pop =new PopupMenu(context,dropDown, Gravity.CENTER);

                    pop.getMenu().add("Showroom Location 1");

                    pop.getMenu().add("Showroom Location 2");

                    pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            String title=menuItem.getTitle().toString();
                            if(title.endsWith("1")){
                                location=1;
                            }else if(title.endsWith("2")){
                                location=2;
                            }
                            dropDown.setText(title);
                            return true;
                        }
                    });
                    pop.show();
                }
            });
        }

        LinearLayout qr=new LinearLayout(context);
        qr.setOrientation(VERTICAL);
        qr.setLayoutParams(lpp);
        qr.setPadding(20,20,20,20);
        qr.setDividerPadding(20);
        {
            TextView heading = new TextView(context);
            heading.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            heading.setText("QR Code");
            heading.setTextColor(0xff000000);
            heading.setPadding(10,10,10,10);
            heading.setTypeface(Typeface.DEFAULT_BOLD);
            heading.setTextSize(20);
            qr.addView(heading);

            LinearLayout  ll=new LinearLayout(context);
            ll.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ll.setOrientation(HORIZONTAL);
ll.setLayoutDirection(LAYOUT_DIRECTION_RTL);
ll.setDividerPadding(20);
            addButton = new Button(context);
            addButton.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            addButton.setText("Add Item");
            addButton.setTextColor(0xffffffff);
            addButton.setTextSize(15);
            addButton.setBackgroundResource(R.drawable.button_bg);
            addButton.setPadding(00,0,0,00);
            addButton.setOnClickListener(add);
            ll.addView(addButton);


            QRText = new EditText(context);
            QRText.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            QRText.setHint("Enter QR");
            QRText.setTextSize(15);
            QRText.setTextColor(0xff000000);
            QRText.setBackgroundResource(R.drawable.text_bg); 
            QRText.setPadding(20,20,20,20);
            QRText.setTextDirection(TEXT_DIRECTION_LTR);
            ll.addView(QRText);
            qr.addView(ll);

            TextView or=new TextView(context);
            or.setText("OR");
            or.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            or.setTypeface(Typeface.DEFAULT_BOLD);
            or.setTextSize(40);
            or.setTextAlignment(TEXT_ALIGNMENT_CENTER);
            or.setTextColor(0xff000000);
            qr.addView(or);

            scanButton = new Button(context);
            RelativeLayout.LayoutParams left=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            left.addRule(RelativeLayout.ALIGN_PARENT_START);

            scanButton.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            scanButton.setText("Scan QR");
            scanButton.setTextColor(0xffffffff);
            scanButton.setBackgroundResource(R.drawable.button_bg);
            scanButton.setPadding(20,20,20,20);

            //rl.addView(scanButton);
            qr.addView(scanButton);
            
        }
        respLayout.addView(loc);
        respLayout.addView(qr);
        ScrollView sv=new ScrollView(context);
        sv.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tb=new TableView(context);
        tb.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        this.addView(respLayout);
        sv.addView(tb);
        this.addView(sv);

    }
    public int getLocation(){
        return this.location;
    }
    public void setQRText(String s){
        this.QRText.setText(s);
    }
    public String getQRText(){
        return QRText.getText().toString();
    }

    public void setQrListener(OnClickListener qr) {
       scanButton.setOnClickListener(qr);
    }

    public void setAddListener(OnClickListener add) {
        addButton.setOnClickListener(add);
    }

}
