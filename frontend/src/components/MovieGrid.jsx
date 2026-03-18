import React from 'react';
import MovieCard from './MovieCard';

function MovieGrid({ movies, onMovieClick }) {
  if (!movies || movies.length === 0) {
    return (
      <div className="empty-state">
        <p>No hay resultados para mostrar.</p>
      </div>
    );
  }

  return (
    <div className="movie-grid">
      {movies.map((movie) => (
        <MovieCard
          key={movie.id}
          movie={movie}
          onClick={() => onMovieClick && onMovieClick(movie.id)}
        />
      ))}
    </div>
  );
}

export default MovieGrid;

