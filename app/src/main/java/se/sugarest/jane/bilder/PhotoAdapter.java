package se.sugarest.jane.bilder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import se.sugarest.jane.bilder.ui.MainActivity;

/**
 * Created by jane on 17-10-31.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoAdapterViewHolder> {

    private final static String LOG_TAG = PhotoAdapter.class.getSimpleName();

    /**
     * An On-click handler that we've defined to make it easy for an Activity to interface with
     * the RecyclerView
     */
    private final PhotoAdapterOnClickHandler mClickHandler;

    private MainActivity mainActivity;

    private ArrayList<String> mPhotoUrlStrings = new ArrayList<>();

    /**
     * Creates a PhotoAdapter.
     *
     * @param clickHandler The on-click handler for this adapter. This single handler is called
     *                     when an item is clicked.
     * @param mainActivity The context to pass down for {@link Picasso}.with(mainActivity)...
     */
    public PhotoAdapter(PhotoAdapterOnClickHandler clickHandler, MainActivity mainActivity) {
        mClickHandler = clickHandler;
        this.mainActivity = mainActivity;
    }

    /**
     * The interface that receives onClick messages.
     */
    public interface PhotoAdapterOnClickHandler {
        void onClick(String photoUrl);

    }

    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param parent   The parent that these ViewHolders are contained within.
     * @param viewType If RecyclerView has more than one type of item (which this one don't)
     *                 this viewType can be used to provide a different layout.
     * @return A new PhotoViewHolder that holds the View for each list item
     */
    @Override
    public PhotoAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.list_view_photo;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new PhotoAdapterViewHolder(view);
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, update the contents of the ViewHolder to display the movie
     * posters for each particular position, using the "position" argument that is conveniently
     * passed in.
     *
     * @param holder   The ViewHolder which should be updated to represent the
     *                 contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(PhotoAdapterViewHolder holder, int position) {
        String currentPhoto = mPhotoUrlStrings.get(position);

        Picasso.with(mainActivity)
                .load(currentPhoto)
                // TODO : if error, provide a drawable
                // .error(R.drawable.photo_on_error)
                .into(holder.mPhotoImageView);
    }

    /**
     * This method simply returns the number of items to display.
     *
     * @return The number of items available on the screen
     */
    @Override
    public int getItemCount() {
        Log.i(LOG_TAG, "The number of items available on the screen: " + mPhotoUrlStrings.size());
        return mPhotoUrlStrings.size();
    }

    /**
     * This method is used to set the photos on a PhotoAdapter.
     */
    public void setPhotoData(ArrayList<String> photoUrls) {
        mPhotoUrlStrings.clear();
        mPhotoUrlStrings.addAll(photoUrls);
        notifyDataSetChanged();
    }

    /**
     * Cache of the children views for a photo image.
     */
    public class PhotoAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        public final ImageView mPhotoImageView;

        public PhotoAdapterViewHolder(View itemView) {
            super(itemView);
            mPhotoImageView = (ImageView) itemView.findViewById(R.id.recyclerview_photos);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}