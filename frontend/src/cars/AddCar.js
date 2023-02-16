import axios from "axios";
import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

export default function AddCar() {
  let navigate = useNavigate();

  const [car, setCar] = useState({
    brand: "",
    model: "",
  });

  const { brand, model } = car;

  const onInputChange = (e) => {
    setCar({ ...car, [e.target.name]: e.target.value });
  };

  const onSubmit = async (e) => {
    e.preventDefault();
    await axios.post("http://localhost:8080/api/car/create", car);
    navigate("/cars");
  };

  return (
    <div className="container">
      <div className="row">
        <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
          <h2 className="text-center m-4">Add Car</h2>
          <form onSubmit={(e) => onSubmit(e)}>
            <div className="mb-3">
              <label htmlFor="Brand" className="form-label">
                Brand
              </label>
              <input
                type={"text"}
                className="form-control"
                placeholder="Enter the Brand of the Car"
                name="brand"
                value={brand}
                onChange={(e) => onInputChange(e)}
              />
            </div>
            <div className="mb-3">
              <label htmlFor="Model" className="form-label">
                Model
              </label>
              <input
                type={"text"}
                className="form-control"
                placeholder="Enter the Model of the Car"
                name="model"
                value={model}
                onChange={(e) => onInputChange(e)}
              />
            </div>
            <button type="submit" className="btn btn-outline-primary">
              Submit
            </button>
            <Link className="btn btn-outline-danger mx-2" to="/cars">
              Cancel
            </Link>
          </form>
        </div>
      </div>
    </div>
  );
}
