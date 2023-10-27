import "./App.css";
import { useState } from "react";
import Main from "./pages/Main";
import Login from "./pages/Login";
import { STATE, StateContext } from "./utils/state";
import { Route, Routes } from "react-router";
import { BrowserRouter } from "react-router-dom";

function App() {
  const [global, setGlobal] = useState(STATE);

  return (
    <BrowserRouter>
      <StateContext.Provider value={{ global, setGlobal }}>
        <Routes>
          <Route path="/login" element={<Login />}></Route>
          <Route path="*" element={<Main />}></Route>
        </Routes>
      </StateContext.Provider>
    </BrowserRouter>
  );
}

export default App;
