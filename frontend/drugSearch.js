async function fetchDrugData() {
  const drugName = document.getElementById("drug-name").value;
  const apiUrl = `https://api.fda.gov/drug/label.json?search=generic_name:${drugName}`;

  try {
    const response = await fetch(apiUrl);
    const data = await response.json();

    displayDrugData(data.results);
  } catch (error) {
    document.getElementById("drug-results").innerHTML =
      "No results found or there was an error fetching data.";
    console.error("Error fetching data:", error);
  }
}

function displayDrugData(drugData) {
  const resultsDiv = document.getElementById("drug-results");
  resultsDiv.innerHTML = ""; // Clear previous results

  drugData.forEach((drug) => {
    const drugInfo = `
      <div class="drug-info">
        <h3>${
          drug.openfda.brand_name ? drug.openfda.brand_name[0] : "Unknown Brand"
        }</h3>
        <p><strong>Generic Name:</strong> ${
          drug.openfda.generic_name ? drug.openfda.generic_name[0] : "N/A"
        }</p>
        <p><strong>Purpose:</strong> ${
          drug.purpose ? drug.purpose[0] : "N/A"
        }</p>
        <p><strong>Usage:</strong> ${
          drug.indications_and_usage ? drug.indications_and_usage[0] : "N/A"
        }</p>
      </div>
    `;
    resultsDiv.innerHTML += drugInfo;
  });
}
