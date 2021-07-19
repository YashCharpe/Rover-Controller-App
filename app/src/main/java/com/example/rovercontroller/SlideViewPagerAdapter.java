package com.example.rovercontroller;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SlideViewPagerAdapter extends PagerAdapter {


    Context ctx;

    public SlideViewPagerAdapter(Context ctx)
    {
        this.ctx=ctx;
    }


    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater layoutInflater= (LayoutInflater) ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_screen,container,false);

        ImageView logo = view.findViewById(R.id.login_slide_logo);
        ImageView ind1 = view.findViewById(R.id.ind1);
        ImageView ind2 = view.findViewById(R.id.ind2);
        ImageView ind3 = view.findViewById(R.id.ind3);

        TextView title = view.findViewById(R.id.title);
        TextView desc = view.findViewById(R.id.desc);

        ImageView next = view.findViewById(R.id.next);
        ImageView back = view.findViewById(R.id.back);

        Button get_started_btn=view.findViewById(R.id.get_started_btn);

        get_started_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetStartedPageViewActivity.viewPager.setCurrentItem(position+1);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetStartedPageViewActivity.viewPager.setCurrentItem(position-1);
            }
        });


        switch (position)
        {
            case 0:
                logo.setImageResource(R.drawable.login_page_view);
                ind1.setImageResource(R.drawable.selected);
                ind2.setImageResource(R.drawable.unselected);
                ind3.setImageResource(R.drawable.unselected);
                title.setText("Create Account");
                desc.setText("Create an Account & Login to App");
                back.setVisibility(View.GONE);
                next.setVisibility(View.VISIBLE);
                get_started_btn.setVisibility(View.GONE);
                break;
            case 1:
                logo.setImageResource(R.drawable.bluetoothpageview);
                ind1.setImageResource(R.drawable.unselected);
                ind2.setImageResource(R.drawable.selected);
                ind3.setImageResource(R.drawable.unselected);
                title.setText("Connect to Arduino");
                desc.setText("Connect to The Arduino Using Bluetooth of Your Device");
                back.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
                get_started_btn.setVisibility(View.GONE);
                break;
            case 2:
                logo.setImageResource(R.drawable.control_page_view);
                ind1.setImageResource(R.drawable.unselected);
                ind2.setImageResource(R.drawable.unselected);
                ind3.setImageResource(R.drawable.selected);
                title.setText("Control Your Rover");
                desc.setText("And That's it! Control Your Rover On Your Handtips");
                back.setVisibility(View.VISIBLE);
                next.setVisibility(View.GONE);
                get_started_btn.setVisibility(View.VISIBLE);
                break;
        }

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
