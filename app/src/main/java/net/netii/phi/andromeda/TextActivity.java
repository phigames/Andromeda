package net.netii.phi.andromeda;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import net.netii.phi.andromeda.perseus.PerseusAnalysis;
import net.netii.phi.andromeda.perseus.PerseusSection;
import net.netii.phi.andromeda.perseus.PerseusData;
import net.netii.phi.andromeda.perseus.PerseusWork;

import java.util.ArrayList;

public class TextActivity extends AppCompatActivity {

    public static int highlightColor;

    private LinearLayout activityLayout;
    private ScrollView textScrollView;
    private TextView textView;
    private TextUtils.WordSpan highlightSpan;
    private View dividerView;
    private RelativeLayout researchLayout;
    private ExpandableListView analysesList;
    private GestureDetectorCompat gestureDetector;
    private Button researchBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        highlightColor = getResources().getColor(R.color.colorAccent);
        setContentView(R.layout.activity_text);
        activityLayout = (LinearLayout) findViewById(R.id.activity_text);
        textScrollView = (ScrollView) findViewById(R.id.scrollView_text);
        textView = (TextView) findViewById(R.id.text);
        researchLayout = (RelativeLayout) findViewById(R.id.relativeLayout_research);
        dividerView = findViewById(R.id.relativeLayout_divider);
        gestureDetector = new GestureDetectorCompat(this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) { }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) { }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (velocityY < 0) {
                    textScrollView.startAnimation(new HeightAnimation(textScrollView, activityLayout.getHeight()));
                    return true;
                }
                return false;
            }
        });
        dividerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    textScrollView.getLayoutParams().height = Math.round(event.getY() + dividerView.getTop() - dividerView.getHeight() / 2);
                    if (textScrollView.getLayoutParams().height < 0) {
                        textScrollView.getLayoutParams().height = 0;
                    } else if (textScrollView.getLayoutParams().height > activityLayout.getHeight() - dividerView.getHeight()) {
                        textScrollView.getLayoutParams().height = activityLayout.getHeight() - dividerView.getHeight();
                    }
                    textScrollView.requestLayout();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    return true;
                }
                return false;
            }
        });
        researchBackButton = (Button) findViewById(R.id.button_divider_back);
        final Context context = this;
        researchBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (highlightSpan != null) {
                    highlightSpan.setHighlight(false);
                    highlightSpan = null;
                    textView.invalidate();
                    Animation slideOutAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_out_right);
                    slideOutAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) { }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            researchLayout.removeView(analysesList);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) { }
                    });
                    analysesList.startAnimation(slideOutAnimation);
                    RotateAnimation rotateAnimation = new RotateAnimation(0, -90, researchBackButton.getWidth() / 2, researchBackButton.getHeight() / 2);
                    rotateAnimation.setDuration(200);
                    rotateAnimation.setFillEnabled(true);
                    rotateAnimation.setFillAfter(true);
                    researchBackButton.startAnimation(rotateAnimation);
                } else {
                    textScrollView.startAnimation(new HeightAnimation(textScrollView, activityLayout.getHeight()));
                }
            }
        });
        PerseusData.Section.loadText((PerseusSection) getIntent().getSerializableExtra("section"), new TextUtils.WordClickedCallback() {
            @Override
            public void onWordClicked(String word, TextUtils.WordSpan span) {
                research(TextUtils.removePunctuation(word));
                if (highlightSpan != null) {
                    highlightSpan.setHighlight(false);
                }
                span.setHighlight(true);
                highlightSpan = span;
                textView.invalidate();
                researchBackButton.clearAnimation();
                HeightAnimation heightAnimation = new HeightAnimation(textScrollView, 200);
                final TextUtils.WordSpan scrollSpan = highlightSpan;
                heightAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) { }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        int spanOffset = ((SpannableString) textView.getText()).getSpanStart(scrollSpan);
                        int scrollLine = Math.max(textView.getLayout().getLineForOffset(spanOffset) - 1, 0);
                        textScrollView.smoothScrollTo(0, textView.getLayout().getLineTop(scrollLine));
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) { }
                });
                textScrollView.startAnimation(heightAnimation);
            }
        }, new PerseusData.DataLoadedCallback<PerseusSection>() {
            @Override
            public void onLoaded(PerseusSection section) {
                /*StringWriter stringWriter = new StringWriter();
                try {
                    TransformerFactory.newInstance().newTransformer().transform(new DOMSource(section.getTextNode()), new StreamResult(stringWriter));
                } catch (TransformerException e) {
                    e.printStackTrace();
                }
                textView.setText(Html.fromHtml(stringWriter.getBuffer().toString(), null, null));*/
                textView.setText(section.getText());
                textView.setMovementMethod(LinkMovementMethod.getInstance());
            }
        });
    }

    private void research(String word) {
        final Context context = this;
        PerseusData.Research.loadAnalyses(word, new PerseusData.DataLoadedCallback<ArrayList<PerseusAnalysis>>() {
            @Override
            public void onLoaded(ArrayList<PerseusAnalysis> analyses) {
                analysesList = (ExpandableListView) LayoutInflater.from(context).inflate(R.layout.analyses_list, null, true);
                ViewCompat.setNestedScrollingEnabled(analysesList, true);
                AnalysesListAdapter adapter = new AnalysesListAdapter(context, analyses);
                analysesList.setAdapter(adapter);
                researchLayout.removeAllViews();
                researchLayout.addView(analysesList);
            }
        });
    }

    private class AnalysesListAdapter extends BaseExpandableListAdapter {

        private Context context;
        private ArrayList<String> lemmas;
        private ArrayList<ArrayList<PerseusAnalysis>> analyses;

        AnalysesListAdapter(Context context, ArrayList<PerseusAnalysis> analyses) {
            this.context = context;
            this.lemmas = new ArrayList<String>();
            this.analyses = new ArrayList<ArrayList<PerseusAnalysis>>();
            for (PerseusAnalysis analysis : analyses) {
                int index = this.lemmas.indexOf(analysis.getLemma());
                if (index == -1) {
                    this.lemmas.add(analysis.getLemma());
                    this.analyses.add(new ArrayList<PerseusAnalysis>());
                    index = this.lemmas.size() - 1;
                }
                this.analyses.get(index).add(analysis);
            }
        }

        @Override
        public int getGroupCount() {
            return lemmas.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return analyses.get(groupPosition).size();
        }

        @Override
        public String getGroup(int groupPosition) {
            return lemmas.get(groupPosition);
        }

        @Override
        public PerseusAnalysis getChild(int groupPosition, int childPosition) {
            return analyses.get(groupPosition).get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.analyses_list_group, parent, false);
                viewHolder = new ViewHolder(
                        (TextView) convertView.findViewById(R.id.textView_analyses_list_group_firstLine),
                        (TextView) convertView.findViewById(R.id.textView_analyses_list_group_secondLine));
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            String lemma = getGroup(groupPosition);
            viewHolder.firstLine.setText(lemma);
            viewHolder.secondLine.setText("asdf");
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.analyses_list_item, parent, false);
                viewHolder = new ViewHolder(
                        (TextView) convertView.findViewById(R.id.textView_analyses_list_item_firstLine),
                        (TextView) convertView.findViewById(R.id.textView_analyses_list_item_secondLine));
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            PerseusAnalysis analysis = getChild(groupPosition, childPosition);
            viewHolder.firstLine.setText(analysis.getForm());
            viewHolder.secondLine.setText(analysis.getProperties());
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }

        private class ViewHolder {

            TextView firstLine, secondLine;

            ViewHolder(TextView firstLine, TextView secondLine) {
                this.firstLine = firstLine;
                this.secondLine = secondLine;
            }

        }

    }

    public class HeightAnimation extends Animation {

        private View view;
        private int startHeight, targetHeight;

        HeightAnimation(View view, int targetHeight) {
            this.view = view;
            this.startHeight = view.getHeight();
            this.targetHeight = targetHeight;
            setDuration(200);
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            view.getLayoutParams().height = Math.round(startHeight + (targetHeight - startHeight) * interpolatedTime);
            view.requestLayout();
        }

    }

}
