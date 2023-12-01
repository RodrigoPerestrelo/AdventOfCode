# Part1

def treatline(string):
    num1 = ''
    num2 = ''
    for char in string:
        if char.isdigit():
            if num1 == '':
                num1 = char
            else:
                num2 = char
    return [num1 + num2] if (num2 != '') else [num1 + num1]


list_nums = []
with open('input.txt', 'r') as file:
    for line in file:
        list_nums += treatline(line)
    list_nums = [int(x) for x in list_nums]
print(sum(list_nums))
