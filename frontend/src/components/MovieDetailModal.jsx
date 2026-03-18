import React from 'react';
import Loader from './Loader';

const FALLBACK_POSTER =
  'https://via.placeholder.com/300x450/1f2933/9fb3c8?text=Sin+imagen';

function MovieDetailModal({ isOpen, onClose, movie, loading }) {
  if (!isOpen) return null;

  const handleBackdropClick = (event) => {
    if (event.target.classList.contains('modal-backdrop')) {
      onClose && onClose();
    }
  };

  const poster = movie?.posterUrl || FALLBACK_POSTER;

  return (
    <div className="modal-backdrop" onClick={handleBackdropClick}>
      <div className="modal">
        <button className="modal-close" onClick={onClose} aria-label="Cerrar">
          ×
        </button>

        {loading || !movie ? (
          <div className="modal-loading">
            <Loader />
          </div>
        ) : (
          <div className="modal-content">
            <div className="modal-poster-column">
              <img src={poster} alt={movie.title} className="modal-poster" />
            </div>
            <div className="modal-info-column">
              <h2 className="modal-title">
                {movie.title} <span className="modal-year">({movie.year})</span>
              </h2>
              <p className="modal-meta">
                <span>{movie.genre}</span> · <span>{movie.runtime}</span> ·{' '}
                <span>{movie.language}</span>
              </p>

              <p className="modal-plot">{movie.plot}</p>

              <div className="modal-detail-row">
                <span className="detail-label">Director:</span>
                <span className="detail-value">{movie.director || 'No disponible'}</span>
              </div>
              <div className="modal-detail-row">
                <span className="detail-label">Actores:</span>
                <span className="detail-value">{movie.actors || 'No disponible'}</span>
              </div>
              <div className="modal-detail-row">
                <span className="detail-label">País:</span>
                <span className="detail-value">{movie.country || 'No disponible'}</span>
              </div>

              <div className="modal-rating-row">
                <span className="rating-label">IMDb rating</span>
                <span className="rating-value">
                  {movie.imdbRating && movie.imdbRating !== 'N/A'
                    ? `${movie.imdbRating} / 10`
                    : 'No disponible'}
                </span>
              </div>

              {movie.ratings && movie.ratings.length > 0 && (
                <div className="modal-extra-ratings">
                  {movie.ratings.map((rating) => (
                    <span key={rating.source} className="rating-chip">
                      {rating.source}: {rating.value}
                    </span>
                  ))}
                </div>
              )}
            </div>
          </div>
        )}
      </div>
    </div>
  );
}

export default MovieDetailModal;

