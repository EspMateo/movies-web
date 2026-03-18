import React, { useState } from 'react';

function SearchBar({ value, onSearch }) {
  const [localValue, setLocalValue] = useState(value || '');

  const handleSubmit = (event) => {
    event.preventDefault();
    if (onSearch) {
      onSearch(localValue);
    }
  };

  const handleChange = (event) => {
    setLocalValue(event.target.value);
  };

  return (
    <form className="search-bar" onSubmit={handleSubmit}>
      <input
        type="text"
        className="search-input"
        placeholder="Buscar por título, ej: Batman, Avengers..."
        value={localValue}
        onChange={handleChange}
      />
      <button type="submit" className="search-button">
        Buscar
      </button>
    </form>
  );
}

export default SearchBar;

