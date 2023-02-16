import { Navigate, Route, BrowserRouter as Router, Routes, useNavigate } from "react-router-dom";
import React, { useState } from 'react';

import axios from 'axios';

export default function LoginPage(props) {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const navigate = useNavigate();
  const handleLogin = async () => {
    try {
       const response = await axios.post('http://localhost:8080/api/login', { username, password });
       const token = response.data.token;
       if(!response.data.token) {
          alert('Incorrect username or password, please try again')
       }else{
        console.log(token);
        props.setAuthenticatedState(true);
        navigate('/')
       
        axios.defaults.headers.common['x-api-key'] = `${token}`;
      
       }
      
    } catch (error) {
      console.error(error);
      // handle login error
    }
  }

  return (
    <div>
      <label>
        Username:
        <input type="text" value={username} onChange={(event) => setUsername(event.target.value)} />
      </label>
      <label>
        Password:
        <input type="password" value={password} onChange={(event) => setPassword(event.target.value)} />
      </label>
      <button onClick={handleLogin}>Login</button>
    </div>
  );
}

