import android.view.*;
import android.widget.*;
import androidx.constraintlayout.widget.*;

/* @Author Deivison Joel */
@PanelInflateMenu("CustomPanels/")
public class test extends ViewPanel {

  public test() {
    // Zero-argument constructor is required
  } 

  // Runs whenever the panel is attached to a HUB
  // You should inflate all the views you would like to attach to the panel within this method.
  public View onAttach() {
    super.setTittle("git");

    // Root layout
    LinearLayout rootLayout = new ALinearLayout();
    rootLayout.setOrientation(LinearLayout.VERTICAL);
    rootLayout.setBackgroundColor(Color.Android.parseColor("#FFFFFF"));
    rootLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    rootLayout.setPadding(24, 24, 24, 24);

    // "New" button
    Button newButton = new AButton();
    newButton.setText("+ New");
    newButton.setAllCaps(false);
    newButton.setGravity(Gravity.CENTER);
    LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    buttonParams.gravity = Gravity.START;
    newButton.setLayoutParams(buttonParams);
    newButton.setOnClickListener(
        new View.OnClickListener() {
          public void onClick(View v) {
            Toast.showText("Button clicked", Toast.LENGTH_SHORT);
            Editor.inflateAnchoredFloatingPanelInDP(v, Editor.AnchorSide.Below, new test(), 100, 80);
          }
        });
        
    AEditText editText =new  AEditText();
    editText.setGravity(Gravity.CENTER);
    LinearLayout.LayoutParams text =new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, 0, 1 // Fill remaining space vertically
            );
    editText.setLayoutParams(text);
    // Center text
    ATextView centerText = new ATextView();
    centerText.setText("This is a sample layout");
    centerText.setTextColor(Color.Android.BLACK);
    centerText.setTextSize(16);
    centerText.setGravity(Gravity.CENTER);

    LinearLayout.LayoutParams textParams =
        new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, 0, 1 // Fill remaining space vertically
            );
    centerText.setLayoutParams(textParams);

    // Add views to root layout
    rootLayout.addView(newButton);
    rootLayout.addView(centerText);
    rootLayout.addView(editText);

    return rootLayout;
  }

  // Run every frame in UI thread doesn't matter if panel is visible or not
  public void updateUI() {}

  // Run every frame in UI thread if panel is visible
  public void updateUIVisible() {}

  // Run every frame in UI thread if panel is not visible
  public void minimizedUpdate() {}

  // Runs whenever the user taps to select the panel from the HUB panel list
  public void onBindView() {}

  // Runs whenever the user switches to another panel in the HUB
  public void onUnbindView() {}

  // Runs whenever the panel is removed from the HUB
  // The views will be discarded, you will inflate it again when it is attached to another HUB
  public void onDetach() {}
}
