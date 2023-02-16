import axios from "axios";
import React, { useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";

export default function AddGarage() {
  let navigate = useNavigate();

  const [garage, setGarage] = useState({
    name: "",
    location: "",
  });

  const { id } = useParams();

  const { name, location } = garage;

  const onInputChange = (e) => {
    setGarage({ ...garage, [e.target.name]: e.target.value });
  };

  const onSubmit = async (e) => {
    e.preventDefault();
    await axios.post(`http://localhost:8080/api/owner/${id}/garage`, garage);
    navigate("/");
  };

  return (
    <div className="container">
      <div className="row">
        <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
          <h2 className="text-center m-4">Register Garage</h2>
          <form onSubmit={(e) => onSubmit(e)}>
            <div className="mb-3">
              <label htmlFor="Name" className="form-label">
                Name
              </label>
              <input
                type={"text"}
                className="form-control"
                placeholder="Enter the Name of the Garage"
                name="name"
                value={name}
                onChange={(e) => onInputChange(e)}
              />
            </div>
            <div className="mb-3">
              <label htmlFor="Location" className="form-label">
                Location
              </label>
              <input
                type={"text"}
                className="form-control"
                placeholder="Enter the Location of the Garage"
                name="location"
                value={location}
                onChange={(e) => onInputChange(e)}
              />
            </div>
            <button type="submit" className="btn btn-outline-primary">
              Submit
            </button>
            <Link className="btn btn-outline-danger mx-2" to="/">
              Cancel
            </Link>
          </form>
        </div>
      </div>
    </div>
  );
}
