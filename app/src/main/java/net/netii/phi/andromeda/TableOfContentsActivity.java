package net.netii.phi.andromeda;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.netii.phi.andromeda.perseus.PerseusSection;
import net.netii.phi.andromeda.perseus.PerseusData;
import net.netii.phi.andromeda.perseus.PerseusTableOfContents;
import net.netii.phi.andromeda.perseus.PerseusWork;
import net.netii.phi.andromeda.perseus.PerseusWorkInfo;

import java.util.ArrayList;
import java.util.Arrays;

public class TableOfContentsActivity extends AppCompatActivity {

    private PerseusWork work;
    private RelativeLayout listLayout;
    private ArrayList<ListView> listViews;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_of_contents);
        work = new PerseusWork((PerseusWorkInfo) getIntent().getSerializableExtra("info"));
        listLayout = (RelativeLayout) findViewById(R.id.layout_sections);
        listViews = new ArrayList<ListView>();
        progressBar = (ProgressBar) findViewById(R.id.progressBar_toc);
        PerseusData.TableOfContents.load(this, work, new PerseusData.DataLoadedCallback<PerseusTableOfContents>() {
            @Override
            public void onLoaded(PerseusTableOfContents toc) {
                addSectionsList(toc.getSections());
            }
        });
    }

    private void addSectionsList(PerseusSection[] sections) {
        final ListView listView = (ListView) LayoutInflater.from(this).inflate(R.layout.sections_list, null);
        ViewCompat.setNestedScrollingEnabled(listView, true);
        SectionsListAdapter adapter = new SectionsListAdapter(this, new ArrayList<PerseusSection>(Arrays.asList(sections)));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PerseusSection clickedSection = (PerseusSection) parent.getItemAtPosition(position);
                if (clickedSection.getSubsections().length > 0) {
                    addSectionsList(clickedSection.getSubsections());
                } else {
                    onSectionSelected(clickedSection);
                }
            }
        });
        listView.setAdapter(adapter);
        listLayout.addView(listView);
        if (listViews.size() > 0) {
            final ListView oldListView = listViews.get(listViews.size() - 1);
            Animation slideOutAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_out_left);
            slideOutAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) { }

                @Override
                public void onAnimationEnd(Animation animation) {
                    oldListView.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) { }
            });
            oldListView.startAnimation(slideOutAnimation);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
        listView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in_right));
        listViews.add(listView);
    }

    private void removeSectionsList() {
        final ListView listView = listViews.get(listViews.size() - 1);
        Animation slideOutAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_out_right);
        slideOutAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                listLayout.removeView(listView);
                listViews.remove(listView);
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });
        if (listViews.size() >= 2) {
            ListView previousListView = listViews.get(listViews.size() - 2);
            previousListView.setVisibility(View.VISIBLE);
            previousListView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in_left));
        }
        listView.startAnimation(slideOutAnimation);
    }

    private void onSectionSelected(PerseusSection section) {
        Intent intent = new Intent(this, TextActivity.class);
        intent.putExtra("section", section);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (listViews.size() == 1) {
            super.onBackPressed();
        } else {
            removeSectionsList();
        }
    }

    private class SectionsListAdapter extends ArrayAdapter<PerseusSection> {

        private Context context;
        private PerseusSection[] sections;

        SectionsListAdapter(Context context, ArrayList<PerseusSection> sections) {
            super(context, R.layout.works_list_row, sections);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.works_list_row, parent, false);
                viewHolder = new ViewHolder(
                        (TextView) convertView.findViewById(R.id.textView_works_list_firstLine),
                        (TextView) convertView.findViewById(R.id.textView_works_list_secondLine));
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            PerseusSection section = getItem(position);
            viewHolder.firstLine.setText(section.getHead());
            return convertView;
        }

        private class ViewHolder {

            TextView firstLine, secondLine;

            ViewHolder(TextView firstLine, TextView secondLine) {
                this.firstLine = firstLine;
                this.secondLine = secondLine;
            }

        }

    }

}
