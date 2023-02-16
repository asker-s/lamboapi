import axios from "axios";
import React, { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";

export default function EditOwner() {
  let navigate = useNavigate();

  const { id } = useParams();

  const [owner, setOwner] = useState({
    name: "",
    surname: "",
  });

  const { name, surname } = owner;

  const onInputChange = (e) => {
    setOwner({ ...owner, [e.target.name]: e.target.value });
  };

  useEffect(() => {
    loadOwner();
  }, []);

  const onSubmit = async (e) => {
    e.preventDefault();
    await axios.put(`http://localhost:8080/api/owner/${id}/update`, owner);
    navigate("/");
  };

  const loadOwner = async () => {
    const result = await axios.get(
      `http://localhost:8080/api/owner/${id}/update`
    );
    setOwner(result.data);
  };

  return (
    <div className="container">
      <div className="row">
        <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
          <h2 className="text-center m-4">Edit Owner</h2>
          <form onSubmit={(e) => onSubmit(e)}>
            <div className="mb-3">
              <label htmlFor="Name" className="form-label">
                Name
              </label>
              <input
                type={"text"}
                className="form-control"
                placeholder="Enter your name"
                name="name"
                value={name}
                onChange={(e) => onInputChange(e)}
              />
            </div>
            <div className="mb-3">
              <label htmlFor="Surname" className="form-label">
                Surname
              </label>
              <input
                type={"text"}
                className="form-control"
                placeholder="Enter your surname"
                name="surname"
                value={surname}
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
