package com.example.digitalmoviesearch;

 class Movies {
    String movieName;
    String movieDescription;
    String movieImage;
    String movieRating;
    String movieLaunchDate;
    String movielink;
    String telegramlink;


    public Movies(String movieName, String movieDescription, String movieImage, String movieRating, String movieLaunchDate, String movielink, String telegramlink) {
        this.movieName = movieName;
        this.movieDescription = movieDescription;
        this.movieImage = movieImage;
        this.movieRating = movieRating;
        this.movieLaunchDate = movieLaunchDate;
        this.movielink = movielink;
        this.telegramlink = telegramlink;
    }

    public Movies() {
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieDescription() {
        return movieDescription;
    }

    public void setMovieDescription(String movieDescription) {
        this.movieDescription = movieDescription;
    }

    public String getMovieImage() {
        return movieImage;
    }

    public void setMovieImage(String movieImage) {
        this.movieImage = movieImage;
    }

    public String getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(String movieRating) {
        this.movieRating = movieRating;
    }

    public String getMovieLaunchDate() {
        return movieLaunchDate;
    }

    public void setMovieLaunchDate(String movieLaunchDate) {
        this.movieLaunchDate = movieLaunchDate;
    }

    public String getMovielink() {
        return movielink;
    }

    public void setMovielink(String movielink) {
        this.movielink = movielink;
    }

    public String getTelegramlink() {
        return telegramlink;
    }

    public void setTelegramlink(String telegramlink) {
        this.telegramlink = telegramlink;
    }


}
