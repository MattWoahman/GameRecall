import React, { useState } from "react";
import axios from "axios";

const GameInfo = () => {
  const [gameName, setGameName] = useState(""); // State for user input
  const [gameData, setGameData] = useState(null); // State for fetched game data
  const [loading, setLoading] = useState(false); // State for loading status
  const [error, setError] = useState(null); // State for error handling

  // Function to fetch game data using Axios
  const fetchGameInfo = async () => {
    if (!gameName.trim()) {
      setError("Please enter a game name.");
      return;
    }

    setLoading(true);
    setError(null);

    try {
      const response = await axios.get(
        `http://localhost:8080/steam/game?name=${encodeURIComponent(gameName)}`
      );

      setGameData(response.data);
    } catch (error) {
      setError(error.response?.data?.message || "Failed to fetch game data.");
      setGameData(null); // Clear any previous data
    } finally {
      setLoading(false);
    }
  };

  // Handle form submission
  const handleSubmit = (e) => {
    e.preventDefault(); // Prevent the default form submission behavior
    fetchGameInfo();
  };

  return (
    <div className="game-info-container">
  <form onSubmit={handleSubmit} className="game-form">
    <input
      type="text"
      value={gameName}
      onChange={(e) => setGameName(e.target.value)}
      placeholder="Enter game name"
      className="game-input"
    />
    <button type="submit" disabled={loading} className="submit-button">
      {loading ? "Loading..." : "Get Game Info"}
    </button>
  </form>

  {error && <div className="error-message">Error: {error}</div>}

  {gameData && (
    <div className="game-details">
      <h2 className="game-title">{gameData.name}</h2>
      <p className="game-description">{gameData.short_description}</p>

      <div className="game-section">
        <h3>Developers</h3>
        <ul className="game-list">
          {gameData.developers?.map((developer, index) => (
            <li key={index} className="game-list-item">{developer}</li>
          ))}
        </ul>
      </div>

      <div className="game-section">
        <h3>Publishers</h3>
        <ul className="game-list">
          {gameData.publishers?.map((publisher, index) => (
            <li key={index} className="game-list-item">{publisher}</li>
          ))}
        </ul>
      </div>

      <div className="game-section">
        <h3>Release Date</h3>
        <p className="release-date">
          {gameData.release_date?.date}{" "}
          {gameData.release_date?.coming_soon && "(Coming Soon)"}
        </p>
      </div>

      <div className="game-section">
        <h3>Categories</h3>
        <ul className="game-list">
          {gameData.categories?.map((category, index) => (
            <li key={index} className="game-list-item">
              {category.description} (ID: {category.id})
            </li>
          ))}
        </ul>
      </div>

      <div className="game-section">
        <h3>Genres</h3>
        <ul className="game-list">
          {gameData.genres?.map((genre, index) => (
            <li key={index} className="game-list-item">
              {genre.description} (ID: {genre.id})
            </li>
          ))}
        </ul>
      </div>
    </div>
  )}
</div>
  );
};

export default GameInfo;
