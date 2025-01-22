import requests
import json

BASE_URL = "http://localhost:8080/person"

def test_get_all_persons():
    response = requests.get(BASE_URL)
    print("GET /person")
    print(response.status_code)
    print(response.json())

def test_add_person():
    new_person = {
        "name": "Ana Souza",
        "birthDate": "1990-01-01",
        "admissionDate": "2021-01-01"
    }
    response = requests.post(BASE_URL, json=new_person)
    print("POST /person")
    print(response.status_code)
    print(response.text)

def test_delete_person(person_id):
    response = requests.delete(f"{BASE_URL}/{person_id}")
    print(f"DELETE /person/{person_id}")
    print(response.status_code)
    print(response.text)

def test_update_person(person_id):
    updated_person = {
        "id": person_id,
        "name": "Ana Souza Updated",
        "birthDate": "1990-01-01",
        "admissionDate": "2021-01-01"
    }
    response = requests.put(f"{BASE_URL}/{person_id}", json=updated_person)
    print(f"PUT /person/{person_id}")
    print(response.status_code)
    print(response.text)

def test_patch_person(person_id):
    updates = {
        "name": "Ana Souza Patched"
    }
    response = requests.patch(f"{BASE_URL}/{person_id}", json=updates)
    print(f"PATCH /person/{person_id}")
    print(response.status_code)
    print(response.text)

def test_get_person_by_id(person_id):
    response = requests.get(f"{BASE_URL}/{person_id}")
    print(f"GET /person/{person_id}")
    print(response.status_code)
    print(response.json())

def test_get_person_age(person_id, output):
    response = requests.get(f"{BASE_URL}/{person_id}/age", params={"output": output})
    print(f"GET /person/{person_id}/age?output={output}")
    print(response.status_code)
    print(response.text)

def test_get_person_salary(person_id, output):
    response = requests.get(f"{BASE_URL}/{person_id}/salary", params={"output": output})
    print(f"GET /person/{person_id}/salary?output={output}")
    print(response.status_code)
    print(response.text)

if __name__ == "__main__":
    test_get_all_persons()
    test_add_person()
    test_get_all_persons()
    test_delete_person(4)  # Assumindo que o ID 4 foi criado
    test_update_person(1)
    test_patch_person(1)
    test_get_person_by_id(1)
    test_get_person_age(1, "years")
    test_get_person_salary(1, "full")