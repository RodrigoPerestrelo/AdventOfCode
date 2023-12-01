# Part2

str_parser = {word: index + 1 for index, word in enumerate(['one', 'two', 'three', 'four', 'five', 'six',
                                                            'seven', 'eight', 'nine'])}


def treatline(string):
    num1 = ''
    num2 = ''
    word = ''
    for char in string:
        word = word + char
        if char.isdigit():
            word = ''
            if num1 == '':
                num1 = char
            else:
                num2 = char
        else:
            if any(key in word for key in str_parser):
                key = next(key for key in str_parser if key in word)
                if num1 == '':
                    num1 = str(str_parser[key])
                else:
                    num2 = str(str_parser[key])
                word = word[-1]

    return [num1 + num2] if (num2 != '') else [num1 + num1]


list_nums = []
with open('input.txt', 'r') as file:
    for line in file:
        list_nums += treatline(line)
    list_nums = [int(x) for x in list_nums]
print(sum(list_nums))
