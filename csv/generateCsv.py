"""
 * Copyright 2024 Valérian ZOLTOWSKI
 * GitHub: https://github.com/Swansky
 *
 * Permission is granted to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of this software,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR
 * IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
"""

import csv
from datetime import datetime

birthday_format = '%d/%m/%Y'

import random

health_codes = ["A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8",
                "B9", "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "D1", "D2", "D3", "D4", "D5", "D6", "D7",
                "D8", "D9", "E1", "E2", "E3", "E4", "E5", "E6", "E7", "E8", "E9", "F1", "F2", "F3", "F4", "F5", "F6",
                "F7", "F8", "F9", "G1", "G2", "G3", "G4", "G5", "G6", "G7", "G8", "G9", "H1", "H2", "H3", "H4", "H5",
                "H6", "H7", "H8", "H9", "I1", "I2", "I3", "I4", "I5", "I6", "I7", "I8", "I9", "J1", "J2", "J3", "J4",
                "J5", "J6", "J7", "J8", "J9", "K1", "K2", "K3", "K4", "K5", "K6", "K7", "K8", "K9", "L1", "L2", "L3",
                "L4", "L5", "L6", "L7", "L8", "L9", "M1", "M2", "M3", "M4", "M5", "M6", "M7", "M8", "M9", "N1", "N2",
                "N3", "N4", "N5", "N6", "N7", "N8"]
names = ["Jean", "Pierre", "Paul", "Jacques", "Marie", "Sophie", "Julie", "Alice", "Lucie", "Julien", "Nicolas",
         "Thomas", "Alexandre", "Antoine", "Laurent", "François", "Philippe", "Olivier", "Vincent", "Sylvain",
         "Sébastien", "Guillaume", "Romain", "Mathieu", "Maxime", "Benoît", "David", "Christophe", "Fabien", "Frédéric",
         "Emmanuel", "Arnaud", "Cédric", "Cyril", "Damien", "Denis", "Éric", "Étienne", "Gilles", "Grégory", "Hervé",
         "Jean-François", "Jean-Luc", "Jean-Michel", "Jean-Pierre", "Jérôme", "Laurent", "Lionel", "Marc", "Michel",
         "Nicolas", "Olivier", "Pascal", "Patrice", "Patrick", "Philippe", "Pierre", "Renaud", "Sébastien", "Stéphane",
         "Thierry", "Vincent", "Xavier", "Yann", "Yves", "Alain", "Albert", "Alexandre", "Alfred", "André", "Antoine",
         "Armand", "Arthur", "Auguste", "Benjamin", "Bernard", "Bertrand", "Charles", "Christian", "Claude", "Daniel",
         "David", "Denis", "Édouard", "Émile", "Étienne", "Fernand", "François", "Frédéric", "Gabriel", "Georges",
         "Gérard", "Gilbert", "Gustave", "Henri", "Hervé", "Hubert", "Jacques", "Jean", "Jérôme", "Joseph", "Julien",
         "Louis", "Lucien", "Marcel", "Marius", "Maurice", "Maxime", "Michel", "Nicolas", "Noël", "Olivier", "Pascal",
         "Patrice", "Paul", "Philippe", "Pierre", "Raoul", "Raymond", "René"]


def get_random_name():
    return random.choice(names)


def get_random_birthdate():
    year = random.randint(1900, 2023)
    month = random.randint(1, 12)
    day = random.randint(1, 28)
    return datetime(year, month, day).strftime(birthday_format)


def get_random_phone():
    return f'0{random.randint(1, 9)}{random.randint(10000000, 99999999)}'


def generate_french_social_security_number():
    sexe = random.choice([1, 2])
    year = random.randint(1900, 2023)
    month = random.randint(1, 12)
    department_code = random.randint(1, 99)
    commune_code = random.randint(1, 999)
    first_digit = str(sexe)
    year_str = str(year)[-2:]
    month_str = str(month).zfill(2)
    department_str = str(department_code).zfill(2)
    commune_str = str(commune_code).zfill(3)
    order_number = str(random.randint(0, 999)).zfill(3)
    social_security_number = first_digit + year_str + month_str + department_str + commune_str + order_number

    return social_security_number


def gen_data(count=100):
    data = []
    for i in range(count):
        name = get_random_name()
        first_name = get_random_name()
        data.append([
            generate_french_social_security_number(),
            name,
            first_name,
            get_random_birthdate(),
            get_random_phone(),
            f'{name}.{first_name}@gmail.com',
            random.randint(1, 100),
            random.choice(health_codes),
            random.randint(1, 1000) * 0.01])
    return data


header = ["Numero_Securite_Sociale", "Nom", "Prenom", "Date_Naissance", "Numero_Telephone", "E_Mail",
          "ID_Remboursement", "Code_Soin", "Montant_Remboursement"]


def generate_random_timestamp():
    year = random.randint(2010, 2023)
    month = random.randint(1, 12)
    day = random.randint(1, 28)
    hour = random.randint(0, 23)
    minute = random.randint(0, 59)
    second = random.randint(0, 59)
    return datetime(year, month, day, hour, minute, second).strftime('%Y%m%d%H%M%S')


for i in range(1):
    timestamp = generate_random_timestamp()
    filename = f'users_{timestamp}.csv'

    file_path = f'./samples/{filename}'
    with open(file_path, 'w', newline='') as file:
        writer = csv.writer(file)
        writer.writerow(header)
        writer.writerows(gen_data(5))
