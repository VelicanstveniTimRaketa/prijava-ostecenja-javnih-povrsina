import { useEffect, useState } from "react";
import { STATE, StateContext } from "./utils/state";
import { Route, Routes } from "react-router";
import { BrowserRouter } from "react-router-dom";
import { getSavedUser } from "./utils/user";
import Main from "./pages/Main";
import Login from "./pages/Login";
import Register from "./pages/Register";

function App() {
  const [global, setGlobal] = useState(STATE);
  const [loginChecked, setLoginChecked] = useState(false);

  useEffect(() => {
    !global.user && getSavedUser().then(user => {
      user && setGlobal({ ...global, user });
      setLoginChecked(true);
    });
  }, [global, global.user]);

  return (
    <BrowserRouter>
      <StateContext.Provider value={{ global, setGlobal }}>
        {loginChecked &&
          <Routes>
            <Route path="/login" element={<Login />}></Route>
            <Route path="/register" element={<Register />}></Route>
            <Route path="*" element={<Main />}></Route>
          </Routes>
        }
      </StateContext.Provider>
    </BrowserRouter>
  );
}

export default App;
