package com.example.settingspanel;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> settingNames;
    ArrayList<Integer> settingValues;
    ArrayList<String> settingUnits;
    ArrayList<String> displayItemsForListView;
    ArrayAdapter<String> adapter;

    int selectedItemPosition = -1;

    ListView settingsListView;
    TextView editingLabelTextView;
    SeekBar valueSeekBar;
    TextView seekBarValueTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settingsListView = findViewById(R.id.settingsListView);
        editingLabelTextView = findViewById(R.id.editingLabelTextView);
        valueSeekBar = findViewById(R.id.valueSeekBar);
        seekBarValueTextView = findViewById(R.id.seekBarValueTextView);

        settingNames = new ArrayList<>();
        settingValues = new ArrayList<>();
        settingUnits = new ArrayList<>();
        displayItemsForListView = new ArrayList<>();

        // Initial settings
        settingNames.add("Jasność Ekranu");
        settingValues.add(50);
        settingUnits.add("%");

        settingNames.add("Głośność Dźwięków");
        settingValues.add(80);
        settingUnits.add("%");

        settingNames.add("Czas Automatycznej Blokady");
        settingValues.add(30);
        settingUnits.add("s");

        for (int i = 0; i < settingNames.size(); i++) {
            displayItemsForListView.add(settingNames.get(i) + ": " + settingValues.get(i) + settingUnits.get(i));
        }

        adapter = new ArrayAdapter<>(this, R.layout.list_item_setting, R.id.itemTextView, displayItemsForListView);
        settingsListView.setAdapter(adapter);

        editingLabelTextView.setText("Wybierz opcję z listy powyżej");
        seekBarValueTextView.setText("Wartość: -");
        valueSeekBar.setEnabled(false);

        settingsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedItemPosition = position;

                String selectedName = settingNames.get(position);
                int selectedValue = settingValues.get(position);

                editingLabelTextView.setText("Edytujesz: " + selectedName);
                seekBarValueTextView.setText("Wartość: " + selectedValue);
                valueSeekBar.setProgress(selectedValue);
                valueSeekBar.setEnabled(true);
            }
        });

        valueSeekBar.setMax(100);
        valueSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && selectedItemPosition != -1) {
                    seekBarValueTextView.setText("Wartość: " + progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (selectedItemPosition != -1) {
                    int newValue = seekBar.getProgress();
                    settingValues.set(selectedItemPosition, newValue);

                    String newDisplayText = settingNames.get(selectedItemPosition) + ": " + newValue + settingUnits.get(selectedItemPosition);
                    displayItemsForListView.set(selectedItemPosition, newDisplayText);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}