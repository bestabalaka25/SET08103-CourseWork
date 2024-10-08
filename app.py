from flask import Flask, jsonify, request
@app.route('/')
def home():
    return jsonify({"message": "Welcome to the Population Reporting API!"})


# Example endpoint to return country data
@app.route('/countries', methods=['GET'])
def get_countries():
    return jsonify({"countries": ["Country1", "Country2"]})


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)  # This will run the app on all interfaces
