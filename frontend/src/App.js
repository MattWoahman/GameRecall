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
	document.body.style='background:#2f2f2f';
	console.log(gameData);
  return (
    <div style={{ fontFamily: "Arial, sans-serif", padding: "20px" }}>
      <h1 style={{color:"#dcbd51"}}>Steam Game Finder</h1>
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
      <img src="https://i.imgur.com/o36AU9D.png" onClick={fetchGameData} width="100px" height="20px" />

      {loading && <p>Loading...</p>}
      {error && <p style={{ color: "red" }}>{error}</p>}

      {gameData && (
        <div style={{ marginTop: "20px", border: "1px solid #ddd", padding: "10px", borderRadius: "5px" }}>
          <h2 style={{color:"#dcbd51"}}>{gameData.name}</h2>
          <p style={{color:"white"}}><strong>Description:</strong> {gameData.short_description}</p>
          <p style={{color:"white"}}><strong>Developer(s):</strong> {gameData.developers.join(", ")}</p>
          <p style={{color:"white"}}><strong>Publisher(s):</strong> {gameData.publishers.join(", ")}</p>
          <p style={{color:"white"}}><strong>Release Date:</strong> {gameData.release_date.date}</p>

          <h3 style={{color:"white"}} >Categories:</h3>
          <ul style={{color:"white"}}>
            {gameData.categories.map((category) => (
              <li key={category.id}>{category.description}</li>
            ))}
          </ul>

          <h3 style={{color:"white"}}>Genres:</h3>
          <ul style={{color:"white"}}>
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
