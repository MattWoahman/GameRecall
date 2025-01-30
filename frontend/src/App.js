import React, { useState } from "react";
import axios from "axios";

function App() {
  const [gameName, setGameName] = useState("");
  const [gameData, setGameData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const fetchGameData = async () => {
    if (!gameName) return;

    setLoading(true);
    setError(null);

    try {
      const response = await axios.get(`http://localhost:8080/steam/game`, {
        params: { name: gameName }
      });

      setGameData(response.data);
    } catch (err) {
      setError("Game not found or API error.");
    } finally {
      setLoading(false);
    }
  };
	console.log(gameData);
  return (
    <div style={{ fontFamily: "Arial, sans-serif", padding: "20px" }}>
      <h1>Steam Game Finder</h1>
      <input
        type="text"
        placeholder="Enter game name..."
        value={gameName}
        onChange={(e) => setGameName(e.target.value)}
        style={{
          padding: "10px",
          marginRight: "10px",
          width: "300px",
        }}
      />
      <button onClick={fetchGameData} style={{ padding: "10px" }}>
        Search
      </button>

      {loading && <p>Loading...</p>}
      {error && <p style={{ color: "red" }}>{error}</p>}

      {gameData && (
        <div style={{ marginTop: "20px", border: "1px solid #ddd", padding: "10px", borderRadius: "5px" }}>
          <h2>{gameData.name}</h2>
          <p><strong>Description:</strong> {gameData.short_description}</p>
          <p><strong>Developer(s):</strong> {gameData.developers.join(", ")}</p>
          <p><strong>Publisher(s):</strong> {gameData.publishers.join(", ")}</p>
          <p><strong>Release Date:</strong> {gameData.release_date.date}</p>

          <h3>Categories:</h3>
          <ul>
            {gameData.categories.map((category) => (
              <li key={category.id}>{category.description}</li>
            ))}
          </ul>

          <h3>Genres:</h3>
          <ul>
            {gameData.genres.map((genre) => (
              <li key={genre.id}>{genre.description}</li>
            ))}
          </ul>
        </div>
      )}
    </div>
  );
}

export default App;
