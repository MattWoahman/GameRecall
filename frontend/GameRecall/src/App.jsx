import { useState } from 'react'
import reactLogo from './assets/react.svg'
import './App.css'
import Button from "./components/Button";
import GameInfo from './components/GameInfo';

function App() {
  return (
    <div className="App">
      <h1>Steam Game Finder</h1>
        <GameInfo />
      </div>
  );
}


export default App
