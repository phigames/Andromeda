package net.netii.phi.andromeda;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import net.netii.phi.andromeda.perseus.PerseusData;
import net.netii.phi.andromeda.perseus.PerseusWork;

import java.util.ArrayList;

public class WorksListActivity extends AppCompatActivity {

    private ArrayList<PerseusWork> loadedWorks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_works_list);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar_works);
        final ListView listView = (ListView) findViewById(R.id.listView_works);
        PerseusData.Works.load(this, new PerseusData.DataLoadedCallback<ArrayList<PerseusWork>>() {
            @Override
            public void onLoaded(ArrayList<PerseusWork> works) {
                loadedWorks = works;
                final WorksListAdapter adapter = new WorksListAdapter(getApplicationContext(), loadedWorks);
                listView.setAdapter(adapter);
                progressBar.setVisibility(View.INVISIBLE);
            }
        }, true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.update, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.refresh) {
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private class ViewHolder {

        int position;
        TextView firstLine, secondLine;

        ViewHolder(int position, TextView firstLine, TextView secondLine) {
            this.position = position;
            this.firstLine = firstLine;
            this.secondLine = secondLine;
        }

    }

    private class WorksListAdapter extends ArrayAdapter<PerseusWork> implements View.OnClickListener {

        WorksListAdapter(Context context, ArrayList<PerseusWork> works) {
            super(context, R.layout.works_list_row, works);
            setNotifyOnChange(true);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.works_list_row, parent, false);
                viewHolder = new ViewHolder(position, (TextView) convertView.findViewById(R.id.textView_works_list_firstLine), (TextView) convertView.findViewById(R.id.textView_works_list_secondLine));
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.firstLine.setText(getItem(position).info.author + ", " + getItem(position).info.title);
            viewHolder.secondLine.setText(Integer.toString(position));
            convertView.setOnClickListener(this);
            return convertView;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getContext(), TableOfContentsActivity.class);
            int position = ((ViewHolder) view.getTag()).position;
            intent.putExtra("info", loadedWorks.get(position).info);
            startActivity(intent);
        }
    }

}
