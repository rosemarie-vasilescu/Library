package com.example.mylibrary;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {
    public static final String BOOK_ID_KEY = "bookId";
    private TextView txtBookName,txtAuthor, txtPages, txtDescription;
    private Button btnAddToWantToRead, btnAddToAlreadyRead, btnAddToCurrentlyReading, btnAddToFavorite;
    private ImageView bookImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        initViews();
        //TODO: get the data from recycler view
//        String longDesc = "Describe your book in simple, straightforward, "+"\n"+
//                "and consumer-friendly terms. Your description should be at least 150-200 words long."+"\n"+
//                 "Give readers enough information to understand what your book is, what it's about, and "+"\n"+
//                "if they'll like it—key factors in deciding whether to buy your book."+"Describe your book in simple, straightforward, "+"\n"+
//                "and consumer-friendly terms. Your description should be at least 150-200 words long."+"\n"+
//                "Give readers enough information to understand what your book is, what it's about, and "+"\n"+
//                "if they'll like it—key factors in deciding whether to buy your book."+"Describe your book in simple, straightforward, "+"\n"+
//                "and consumer-friendly terms. Your description should be at least 150-200 words long."+"\n"+
//                "Give readers enough information to understand what your book is, what it's about, and "+"\n"+
//                "if they'll like it—key factors in deciding whether to buy your book.";
//        Book book = new Book(1,"IQ84","AJSA ASKJA",1350,"https://i.pinimg.com/236x/52/0c/bd/520cbd29f008036be367cf258447bd18--chip-kidd-haruki-murakami.jpg","A work od maddeling brilliance","Long description");
        Intent intent = getIntent();
        if (null != intent){
            int bookId = intent.getIntExtra(BOOK_ID_KEY,-1);
            if (bookId != -1){
                Book incomingBook = Utils.getInstance(this).getBookById(bookId);
                if (null != incomingBook){
                    setData(incomingBook);

                    handleAlreadyRead(incomingBook);
                    handleWantToReadBooks(incomingBook);
                    handleCurrentlyReadingBooks(incomingBook);
                    handleFavoriteBooks(incomingBook);
                }
            }
        }
    }
    private void  handleFavoriteBooks(final Book book){
        ArrayList<Book> favoriteBooks = Utils.getInstance(this).getFavoriteBooks();
        boolean existInFavoriteBooks = false;
        for (Book b: favoriteBooks ){
            if (b.getId() == book.getId()){
                existInFavoriteBooks = true;
            }
        }

        if (existInFavoriteBooks){
            btnAddToFavorite.setEnabled(false);
        }else{
            btnAddToFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Utils.getInstance(BookActivity.this).addToFavoriteBooks(book)){
                        Toast.makeText(BookActivity.this, "added", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(BookActivity.this, FavoriteBooksActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(BookActivity.this, "wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private void  handleCurrentlyReadingBooks(final Book book){
        ArrayList<Book> currentlyReadingBooks = Utils.getInstance(this).getCurrentlyReadingBooks();
        boolean existInCurrentlyReadBooks = false;
        for (Book b: currentlyReadingBooks ){
            if (b.getId() == book.getId()){
                existInCurrentlyReadBooks = true;
            }
        }

        if (existInCurrentlyReadBooks){
            btnAddToCurrentlyReading.setEnabled(false);
        }else{
            btnAddToCurrentlyReading.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Utils.getInstance(BookActivity.this).addToCurrentlyReadingBooks(book)){
                        Toast.makeText(BookActivity.this, "added", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(BookActivity.this, CurrentlyReadingBooksActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(BookActivity.this, "wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private void  handleWantToReadBooks(final Book book){
        ArrayList<Book> wantToReadBooks = Utils.getInstance(this).getWantToReadBooks();
        boolean existInWantToReadBooks = false;
        for (Book b: wantToReadBooks ){
            if (b.getId() == book.getId()){
                existInWantToReadBooks = true;
            }
        }

        if (existInWantToReadBooks){
            btnAddToWantToRead.setEnabled(false);
        }else{
            btnAddToWantToRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Utils.getInstance(BookActivity.this).addToWantToRead(book)){
                        Toast.makeText(BookActivity.this, "added", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(BookActivity.this, WantToReadActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(BookActivity.this, "wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    /**
    enable and disable button
    add the book to already read book arraylist
     */
    private void handleAlreadyRead(Book book){
        ArrayList<Book> alreadyReadBooks = Utils.getInstance(this).getAlreadyReadBooks();
        boolean existInAlreadyReadBooks = false;
        for (Book b: alreadyReadBooks ){
            if (b.getId() == book.getId()){
                existInAlreadyReadBooks = true;
            }
        }

        if (existInAlreadyReadBooks){
            btnAddToAlreadyRead.setEnabled(false);
        }else{
            btnAddToAlreadyRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   if (Utils.getInstance(BookActivity.this).addToAlreadyRead(book)){
                        Toast.makeText(BookActivity.this, "added", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(BookActivity.this, AlreadyReadBookActivity.class);
                        startActivity(intent);
                   }else{
                        Toast.makeText(BookActivity.this, "wrong", Toast.LENGTH_SHORT).show();
                    }
            }
            });
        }
    }
    private void setData(Book book){

        txtBookName.setText(book.getName());

         txtAuthor.setText(book.getAuthor());

         txtPages.setText(String.valueOf(book.getPages()));
        Log.d(TAG,"pages: Called");
         txtDescription.setText(book.getLongDesc());
        Toast.makeText(this, "in bookactivitydesc", Toast.LENGTH_SHORT).show();
        Glide.with(this)
                .asBitmap().load(book.getImageUrl())
                .into(bookImage);
    }
    private void initViews(){
        txtAuthor = findViewById(R.id.txtAuthorName);
        txtBookName = findViewById(R.id.txtBookName);
        txtPages = findViewById(R.id.txtPages);
        txtDescription = findViewById(R.id.txtDescription);

        btnAddToAlreadyRead = findViewById(R.id.btnAddToAlreadyReadList);
        btnAddToCurrentlyReading = findViewById(R.id.btnAddToCurrencyReading);
        btnAddToWantToRead = findViewById(R.id.btnAddToWantToRead);
        btnAddToFavorite = findViewById(R.id.btnAddToFavorite);

        bookImage = findViewById(R.id.imgBook);
    }

    public TextView getTxtBookName() {
        return txtBookName;
    }

    public void setTxtBookName(TextView txtBookName) {
        this.txtBookName = txtBookName;
    }

    public TextView getTxtAuthor() {
        return txtAuthor;
    }

    public void setTxtAuthor(TextView txtAuthor) {
        this.txtAuthor = txtAuthor;
    }

    public TextView getTxtPages() {
        return txtPages;
    }

    public void setTxtPages(TextView txtPages) {
        this.txtPages = txtPages;
    }

    public TextView getTxtDescription() {
        return txtDescription;
    }

    public void setTxtDescription(TextView txtDescription) {
        this.txtDescription = txtDescription;
    }

    public ImageView getBookImage() {
        return bookImage;
    }

    public void setBookImage(ImageView bookImage) {
        this.bookImage = bookImage;
    }
}