import React, { useEffect, useState } from 'react';
import Header from './components/Header';
import SearchBar from './components/SearchBar';
import MovieGrid from './components/MovieGrid';
import MovieDetailModal from './components/MovieDetailModal';
import Loader from './components/Loader';
import ErrorMessage from './components/ErrorMessage';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080';

function App() {
  const [query, setQuery] = useState('Batman');
  const [movies, setMovies] = useState([]);
  const [totalResults, setTotalResults] = useState(0);
  const [selectedMovieId, setSelectedMovieId] = useState(null);
  const [selectedMovie, setSelectedMovie] = useState(null);
  const [loading, setLoading] = useState(false);
  const [detailLoading, setDetailLoading] = useState(false);
  const [error, setError] = useState(null);

  const fetchMovies = async (searchQuery) => {
    const q = searchQuery.trim();
    if (!q) {
      setMovies([]);
      setTotalResults(0);
      setError('Ingresa un término de búsqueda.');
      return;
    }

    setLoading(true);
    setError(null);

    try {
      const response = await fetch(`${API_BASE_URL}/api/movies/search?query=${encodeURIComponent(q)}`);

      if (!response.ok) {
        throw new Error('Error al buscar películas. Intenta nuevamente.');
      }

      const data = await response.json();

      if (!data.results || data.results.length === 0) {
        setMovies([]);
        setTotalResults(0);
        setError('No se encontraron resultados para tu búsqueda.');
      } else {
        setMovies(data.results);
        setTotalResults(data.totalResults || data.results.length);
      }
    } catch (err) {
      console.error(err);
      setError(err.message || 'Ocurrió un error inesperado.');
      setMovies([]);
      setTotalResults(0);
    } finally {
      setLoading(false);
    }
  };

  const fetchMovieDetail = async (id) => {
    setDetailLoading(true);
    setSelectedMovie(null);

    try {
      const response = await fetch(`${API_BASE_URL}/api/movies/${encodeURIComponent(id)}`);
      if (!response.ok) {
        throw new Error('Error al obtener el detalle de la película.');
      }
      const data = await response.json();
      setSelectedMovie(data);
    } catch (err) {
      console.error(err);
      setError(err.message || 'Ocurrió un error al cargar el detalle.');
    } finally {
      setDetailLoading(false);
    }
  };

  useEffect(() => {
    fetchMovies(query);
  }, []);

  const handleSearch = (newQuery) => {
    setQuery(newQuery);
    fetchMovies(newQuery);
  };

  const handleMovieClick = (movieId) => {
    setSelectedMovieId(movieId);
    fetchMovieDetail(movieId);
  };

  const handleCloseModal = () => {
    setSelectedMovieId(null);
    setSelectedMovie(null);
  };

  return (
    <div className="app">
      <Header />
      <main className="app-main">
        <section className="search-section">
          <SearchBar value={query} onSearch={handleSearch} />
        </section>

        {error && (
          <div className="feedback-row">
            <ErrorMessage message={error} />
          </div>
        )}

        {loading ? (
          <div className="feedback-row">
            <Loader />
          </div>
        ) : (
          <section className="results-section">
            <div className="results-header">
              {totalResults > 0 && (
                <p className="results-count">
                  Mostrando {movies.length} de {totalResults} resultados
                </p>
              )}
            </div>
            <MovieGrid movies={movies} onMovieClick={handleMovieClick} />
          </section>
        )}
      </main>

      {selectedMovieId && (
        <MovieDetailModal
          isOpen={Boolean(selectedMovieId)}
          onClose={handleCloseModal}
          movie={selectedMovie}
          loading={detailLoading}
        />
      )}
    </div>
  );
}

export default App;

