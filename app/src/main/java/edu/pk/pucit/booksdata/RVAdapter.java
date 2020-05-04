package edu.pk.pucit.booksdata;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import static android.content.Context.DOWNLOAD_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {
    Context context;

    private String [] title;
    private String [] Levels;
    private String [] info;
    private String [] booksUrls;
    private String basePath = "https://raw.githubusercontent.com/revolunet/PythonBooks/master/";
    private String [] bimgPaths;

    RVAdapter(Context context , String [] title , String [] Levels , String [] bookInfo , String [] booksUrls , String [] bimgPaths){
        this.context = context;
        this.title = title;
        this.Levels = Levels;
        this.info = bookInfo;
        this.booksUrls = booksUrls;
        this.bimgPaths = bimgPaths;
    }


    @NonNull
    @Override
    public RVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Layout Inflation is done
        View rowView = LayoutInflater.from(context).inflate(R.layout.myrow_layout ,null);

        Log.i("Inflater" ,"Done" );

        // Layout Binding is done
        Log.i("Binding" ,"Done" );
        return new RVAdapter.ViewHolder(rowView);
    }

    // Placing Data in Layout will be dine in this function
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        // Image loading is done by picasso
        Picasso.get().load(basePath + bimgPaths[position]).into(holder.tv_bookbimg);

        Log.i("Path ", basePath + bimgPaths[position]);


        holder.tv_bookName.setText(title[position]);
        holder.tv_Level.setText(Levels[position]);
        holder.tv_bookInfo.setText(info[position]);
        String str = booksUrls[position].substring(booksUrls[position].length() - 4);
        if(str.equals(".zip") || str.equals(".pdf")){
           // change the text of button to "DOWNLOAD"
            holder.tv_btn.setText("DOWNLOAD");
            // set click event on Button and then start downloading file through download manger
            holder.tv_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = booksUrls[position];
                    DownloadManager downloadManager = (DownloadManager)context.getSystemService(context.DOWNLOAD_SERVICE);
                    Uri uri = Uri.parse(url);
                    DownloadManager.Request request = new DownloadManager.Request(uri);
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    Long ref = downloadManager.enqueue(request);
                }
            });
        }
        else{
            holder.tv_btn.setText("READ ONLINE");
            //set click event on Button and then move to google chorme to read book online
            holder.tv_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = booksUrls[position];
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    ContextCompat.startActivity(context, intent, null);
                }
            });
        }

        Log.i("Text Binding" ,"In layout Done" );

        //Log.i("Image Binding" ,"In Layout Done" );
    }

    @Override
    public int getItemCount() {
        return title.length;
    }

    // Binding of Layout Elements is done here
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_bookName;
        public TextView tv_Level;
        public TextView tv_bookInfo;
        public ImageView tv_bookbimg;
        public Button tv_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_bookName = itemView.findViewById(R.id.bookName);
            tv_Level = itemView.findViewById(R.id.Level);
            tv_bookInfo = itemView.findViewById(R.id.info);
            tv_bookbimg = itemView.findViewById(R.id.bimg);
           tv_btn = itemView.findViewById(R.id.button);
        }

    }
}
