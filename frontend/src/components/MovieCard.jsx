import React from 'react';

const FALLBACK_POSTER =
  'https://via.placeholder.com/300x450/1f2933/9fb3c8?text=Sin+imagen';

function MovieCard({ movie, onClick }) {
  const poster = movie.posterUrl || FALLBACK_POSTER;

  return (
    <article className="movie-card" onClick={onClick}>
      <div className="movie-card-poster-wrapper">
        <img src={poster} alt={movie.title} className="movie-card-poster" />
      </div>
      <div className="movie-card-body">
        <h3 className="movie-card-title">{movie.title}</h3>
        <p className="movie-card-meta">
          <span>{movie.year}</span> · <span className="badge">{movie.type}</span>
        </p>
      </div>
    </article>
  );
}

export default MovieCard;

