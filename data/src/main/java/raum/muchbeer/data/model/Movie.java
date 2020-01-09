package raum.muchbeer.data.model;


import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import raum.muchbeer.data.utility.LoadImageGlide;

@Entity(tableName = "movietbl",
        indices = {@Index("id")})
public class Movie  extends BaseObservable implements Parcelable {

    static Context context;
    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;
    @SerializedName("id")
    @Expose
    @PrimaryKey
    private Integer id;
    @SerializedName("video")
    @Expose
    @Ignore
    private Boolean video;
    @SerializedName("vote_average")
    @Expose
    @ColumnInfo(name="averagevote")
    private Double voteAverage;
    @SerializedName("title")
    @Expose
    @ColumnInfo(name="title")
    private String title;
    @SerializedName("popularity")
    @Expose
    @Ignore
    private Double popularity;
    @SerializedName("poster_path")
    @Expose
    @ColumnInfo(name="posterPath")
    private String posterPath;


    @BindingAdapter({"posterPath"})
    public static void loadImage(ImageView imageView, String imageURL){

        String imagePath="https://image.tmdb.org/t/p/w500"+imageURL;

        LoadImageGlide.loadimage(imageView, imagePath, getProgressDrawable(imageView.getContext()));
    }

    @SerializedName("original_language")
    @Expose
    private String originalLanguage;
    @SerializedName("original_title")
    @Expose
    private String originalTitle;
    @SerializedName("genre_ids")
    @Expose
    @Ignore
    private List<Integer> genreIds = new ArrayList<Integer>();
    @SerializedName("backdrop_path")
    @Expose
    @Ignore
    private String backdropPath;
    @SerializedName("adult")
    @Expose
    @Ignore
    private Boolean adult;
    @SerializedName("overview")
    @Expose
    @ColumnInfo(name="overview")
    private String overview;
    @SerializedName("release_date")
    @Expose
    @ColumnInfo(name="releasedate")
    private String releaseDate;

    //paging calling DIFF CALLBACK
    // use for ordering the items in view
    public static DiffUtil.ItemCallback<Movie> DIFF_CALLBACK = new DiffUtil.ItemCallback<Movie>() {
        @Override
        public boolean areItemsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
            return oldItem.getId().equals(newItem.getId());
        }
    };

    public final static Creator<Movie> CREATOR = new Creator<Movie>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return (new Movie[size]);
        }

    }
            ;

    protected Movie(Parcel in) {
        this.voteCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.video = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.voteAverage = ((Double) in.readValue((Double.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.popularity = ((Double) in.readValue((Double.class.getClassLoader())));
        this.posterPath = ((String) in.readValue((String.class.getClassLoader())));
        this.originalLanguage = ((String) in.readValue((String.class.getClassLoader())));
        this.originalTitle = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.genreIds, (Integer.class.getClassLoader()));
        this.backdropPath = ((String) in.readValue((String.class.getClassLoader())));
        this.adult = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.overview = ((String) in.readValue((String.class.getClassLoader())));
        this.releaseDate = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Movie() {
    }

    @Bindable
    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
        notifyPropertyChanged(raum.muchbeer.data.BR.voteCount);
    }

    @Bindable
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
        notifyPropertyChanged(raum.muchbeer.data.BR.id);
    }

    @Bindable
    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
        notifyPropertyChanged(raum.muchbeer.data.BR.video);
    }

    @Bindable
    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
        notifyPropertyChanged(raum.muchbeer.data.BR.voteAverage);
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(raum.muchbeer.data.BR.title);
    }

    @Bindable
    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
        notifyPropertyChanged(raum.muchbeer.data.BR.popularity);
    }

    @Bindable
    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
        notifyPropertyChanged(raum.muchbeer.data.BR.posterPath);
    }

    @Bindable
    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
        notifyPropertyChanged(raum.muchbeer.data.BR.originalLanguage);
    }

    @Bindable
    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
        notifyPropertyChanged(raum.muchbeer.data.BR.originalTitle);
    }

    @Bindable
    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
        notifyPropertyChanged(raum.muchbeer.data.BR.genreIds);
    }

    @Bindable
    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
        notifyPropertyChanged(raum.muchbeer.data.BR.backdropPath);
    }

    @Bindable
    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
        notifyPropertyChanged(raum.muchbeer.data.BR.adult);
    }

    @Bindable
    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
        notifyPropertyChanged(raum.muchbeer.data.BR.overview);
    }

    @Bindable
    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
        notifyPropertyChanged(raum.muchbeer.data.BR.releaseDate);
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(voteCount);
        dest.writeValue(id);
        dest.writeValue(video);
        dest.writeValue(voteAverage);
        dest.writeValue(title);
        dest.writeValue(popularity);
        dest.writeValue(posterPath);
        dest.writeValue(originalLanguage);
        dest.writeValue(originalTitle);
        dest.writeList(genreIds);
        dest.writeValue(backdropPath);
        dest.writeValue(adult);
        dest.writeValue(overview);
        dest.writeValue(releaseDate);
    }

    public int describeContents() {
        return 0;
    }

    public static CircularProgressDrawable getProgressDrawable(Context context) {

        CircularProgressDrawable cpd = new CircularProgressDrawable(context);
        cpd.setStrokeWidth(5f);
        cpd.setCenterRadius(50f);
        cpd.start();
        return cpd;

    }
}
