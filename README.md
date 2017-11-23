# Bilder
A small app to search pictures via Flickrs API.

P.S. To run the app, you have to provide YOUR_API_KEY in se.sugarest.jane.bilder.util.Constants file. 

README

tests

1. Test all UI widgets are displayed on the main screen and detail screen.

2. Test static views, e.g., Search Button on main screen, has the correct text.

3. Test EditText performs as expected.

4. Test PhotoView performs as expected, e.g., Performs double click on the picture.

5. Test the RecyclerView to show pictures on main screen after clicking Search Button with providing a keyword, introducing IdlingResource to control Espresso while the app is doing long-running operations, e.g., retrieving data from REST API during testing.  Perform clicking on one of the picture items, which opens detail screen, using Intent Verification Espresso Test.

6. Test a Toast message is displayed when clicking search button without providing a keyword to search.

7. Test getItemCount() method in PhotoAdapter class.



