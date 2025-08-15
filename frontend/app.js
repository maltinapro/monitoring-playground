const express = require('express');
const axios = require('axios');
const app = express();
const port = 80;

app.get('/', async (req, res) => {
  try {
    const response = await axios.get('http://microservice1:8081/hello');
    res.send(`Frontend -> ${response.data}`);
  } catch (error) {
    res.status(500).send('Error calling microservice1');
  }
});

app.listen(port, () => {
  console.log(`Frontend listening on port ${port}`);
});