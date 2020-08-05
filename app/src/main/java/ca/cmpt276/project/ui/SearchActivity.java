package ca.cmpt276.project.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import ca.cmpt276.project.R;

public class SearchActivity extends AppCompatActivity {
    Intent intent = new Intent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search2);

        createOptionButtons();

    }

    private void createOptionButtons() {
        RadioGroup group = findViewById(R.id.options);

        String[] options = {"All","Favourites"};

        // Create the buttons
        for (int i = 0; i <options.length; i++) {
            final String game_size = options[i];
            RadioButton button = new RadioButton(this);
            button.setText(game_size + "");
            final int m = i;
            // set on-click callbacks
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (m == 0) {
                        intent.putExtra("option", "all");
                    } else {
                        intent.putExtra("option", "favourite");
                    }
                    setResult(RESULT_OK, intent);
                }
            });
            group.addView(button);
        }
    }


}