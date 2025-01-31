import { useState } from 'react'
import reactLogo from './assets/react.svg'
import './App.css'
import Button from "./components/Button";

function App() {
  return (
    <div className="flex justify-center items-center h-screen">
      <Button text="Click Me" onClick={() => alert("Button Clicked!")} />
    </div>
  );
}


export default App
