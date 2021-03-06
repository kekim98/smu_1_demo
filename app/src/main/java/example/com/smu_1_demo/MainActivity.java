package example.com.smu_1_demo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.Map;

public class MainActivity extends AppCompatActivity
{
    private static final String DEMO_PREFERENCE = "DEMO_PREFERENCE";
    private static final String TAG = "git test";
    private ArrayAdapter<String> mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("지원자 목록");

        ListView listview = (ListView) findViewById(R.id.listview);
        mListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        listview.setAdapter(mListAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id)
            {
                String name = (String) adapterView.getAdapter().getItem(i);

                SharedPreferences pref = getSharedPreferences(DEMO_PREFERENCE, MODE_PRIVATE);
                String code = pref.getString(name, "UNKNOW");

                Intent o = new Intent(MainActivity.this, Contact.class);

                o.putExtra("name", name);
                o.putExtra("code", code);

                startActivity(o);
            }
        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                final String name = (String) adapterView.getAdapter().getItem(i);

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("정말로 삭제하시겠습니까?")
                        .setMessage("선택된 사용자 : " + name)
                        .setNegativeButton("아니요", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                dialogInterface.dismiss();
                            }
                        })
                        .setPositiveButton("예", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                SharedPreferences pref = getSharedPreferences(DEMO_PREFERENCE, MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.remove(name);
                                editor.commit();

                                refresh();
                                dialogInterface.dismiss();
                            }
                        })
                        .show();

                return true;
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        refresh();
    }

    private void refresh()
    {
        SharedPreferences pref = getSharedPreferences(DEMO_PREFERENCE, MODE_PRIVATE);
        Map<String, ?> values = pref.getAll();
        mListAdapter.clear();
        for(String key : values.keySet())
        {
            mListAdapter.add(key);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.menutest:
                Intent intent = new Intent(MainActivity.this, JanrActivity.class);
                startActivity(intent);
        }
        return true;
    }
    public void onClick3(View view) {
        Intent intent_web = new Intent(MainActivity.this, WebActivity.class);
        startActivity(intent_web);
    }
    public void onClick2(View view) {
        Intent intent_web2 = new Intent(MainActivity.this, WebActivity2.class);
        startActivity(intent_web2);
    }
}
