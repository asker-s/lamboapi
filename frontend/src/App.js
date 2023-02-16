import "./App.css";
import "../node_modules/bootstrap/dist/css/bootstrap.min.css";

import {
  Navigate,
  Route,
  BrowserRouter as Router,
  Routes,
} from "react-router-dom";
import React, { useState } from "react";

import AccessibleGarages from "./cars/AccessibleGarages";
import AddCar from "./cars/AddCar";
import AddGarage from "./garages/AddGarage";
import AddOwner from "./owners/AddOwner";
import AssignCar from "./garages/AssignCar";
import AssignGarage from "./cars/AssignGarage";
import Cars from "./cars/Cars";
import EditOwner from "./owners/EditOwner";
import Home from "./pages/Home";
import LoginPage from "./auth/LoginPage";
import Navbar from "./layout/Navbar";
import ViewCars from "./garages/ViewCars";
import ViewGarage from "./garages/ViewGarage";
import ViewGarages from "./owners/ViewGarages";
import ViewOwner from "./owners/ViewOwner";

function App() {
  const [authenticated, setAuthenticated] = useState(false);

  function setAuthenticatedState(newState) {
    console.log("settng" + newState);
    setAuthenticated(newState);
    console.log("s" + authenticated);
  }
  const PrivateRoute = ({ Component }) => {
    const auth = null;

    console.log(authenticated);
    return authenticated ? <Component /> : <Navigate to="/login" />;
  };

  return (
    <div className="App">
      <Router>
        <Navbar />
        <Routes>
          <Route
            exact
            path="/login"
            element={
              <LoginPage setAuthenticatedState={setAuthenticatedState} />
            }
          />
          <Route exact path="/" element={<PrivateRoute Component={Home} />} />
          <Route
            exact
            path="/addowner"
            element={<PrivateRoute Component={AddOwner} />}
          />
          <Route
            exact
            path="/editowner/:id"
            element={<PrivateRoute Component={EditOwner} />}
          />
          <Route
            exact
            path="/viewowner/:id"
            element={<PrivateRoute Component={ViewOwner} />}
          />
          <Route
            exact
            path="/viewgarages/:id"
            element={<PrivateRoute Component={ViewGarages} />}
          />
          <Route
            exact
            path="/addgarage/:id"
            element={<PrivateRoute Component={AddGarage} />}
          />
          <Route
            exact
            path="/viewgarage/:ownerId/:garageId"
            element={<PrivateRoute Component={ViewGarage} />}
          />
          <Route
            exact
            path="/cars"
            element={<PrivateRoute Component={Cars} />}
          />
          <Route
            exact
            path="/addcar"
            element={<PrivateRoute Component={AddCar} />}
          />
          <Route
            exact
            path="/viewgarages/:ownerId/:garageId/cars"
            element={<PrivateRoute Component={ViewCars} />}
          />
          <Route
            exact
            path="/owner/:ownerId/garages/:garageId/cars/assign"
            element={<PrivateRoute Component={AssignCar} />}
          />
          <Route
            exact
            path="/cars/:id/garages"
            element={<PrivateRoute Component={AccessibleGarages} />}
          />
          <Route
            exact
            path="/cars/:carId/garages/assign"
            element={<PrivateRoute Component={AssignGarage} />}
          />
        </Routes>
      </Router>
    </div>
  );
}

export default App;
