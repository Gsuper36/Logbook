package madsoft.com.form.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import madsoft.com.form.DataBase.entity.Page;
import madsoft.com.form.Network.Objects.ArticleWp;
import madsoft.com.form.Network.Objects.Title;
import madsoft.com.form.R;
import retrofit2.Call;
import retrofit2.Response;

public class PageRecyclerViewAdapter extends RetrofitWpPaginationAdapter <Page>{

    private ArticleRecyclerViewAdapter.onClickListener onClickListener;

    {
        list = new ArrayList<>();
    }

    public PageRecyclerViewAdapter(ArticleRecyclerViewAdapter.onClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public class PageRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView imageView;
        public TextView titleTextView, descriptionTextView;
        public Button share, download;



        private ArticleWp pageToArticle(Page page){

            ArticleWp articleWp = new ArticleWp();
            Title title = new Title();
            title.setRendered(page.title);
            articleWp.setId(page.id);
            articleWp.setLink(page.shareLink);
            articleWp.setJetpackFeaturedMediaUrl(null);
            articleWp.setModified(page.modified);
            articleWp.setTitle(title);
            return articleWp;

        }

        public PageRecyclerViewHolder(@NonNull View itemView, ArticleRecyclerViewAdapter.onClickListener listener) {
            super(itemView);
            onClickListener = listener;
            imageView = itemView.findViewById(R.id.card_image);
            titleTextView = itemView.findViewById(R.id.card_title);
            descriptionTextView = itemView.findViewById(R.id.card_desc);
            share = itemView.findViewById(R.id.button_share);
            download = itemView.findViewById(R.id.button_download);
            itemView.setOnClickListener(this);
            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(intentCallback == null) return;

                    intentCallback.onShareArticle(pageToArticle(list.get(getAdapterPosition())));//TODO переделать??

                }
            });
            download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   //TODO удалить запись
                }
            });
        }

        @Override
        public void onClick(View v) {
            onClickListener.onItemClick(getAdapterPosition());
        }

        public void bind(Page page){

            titleTextView.setText(page.title);
            descriptionTextView.setText(page.modified);
            imageView.setImageResource(R.drawable.placeholder);//TODO сохранять изображения в кэш



        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);
        return new PageRecyclerViewHolder(view, onClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PageRecyclerViewHolder viewHolder = (PageRecyclerViewHolder) holder;
        viewHolder.bind(list.get(position));
    }

    public Page getItem(int id){//TODO добавить в RETROFIT PAGINATION ADAPTER
        return list.get(id);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onResponse(Call<List<Page>> call, Response<List<Page>> response) {

    }

    @Override
    public void onFailure(Call<List<Page>> call, Throwable t) {

    }

}
