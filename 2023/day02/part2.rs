use std::fs::File;
use std::io::{self, BufRead};

fn treat_line(line: &str) -> i32 {
    let mut red_cubes = 0;
    let mut green_cubes = 0;
    let mut blue_cubes = 0;

    let pieces: Vec<&str> = line.split(':').collect();

    if let Some(game_info) = pieces.get(1) {
        let game_info = game_info.trim().trim_end_matches(';').trim_start();
        let game: Vec<&str> = game_info.split(';').collect();

        for round in game {
            let pieces_game: Vec<&str> = round.split(',').collect();

        let mut count_green = 0;
        let mut count_blue = 0;
        let mut count_red = 0;

            for piece in pieces_game {
                let element = piece.trim();

                let (number_str, colour) = element.split_once(' ').unwrap_or(("", ""));
                let number = number_str.parse::<usize>().unwrap_or_default();

                match colour {
                    "green" => count_green += number,
                    "blue" => count_blue += number,
                    "red" => count_red += number,
                    _ => (),
                }

                if count_red > red_cubes {
                    red_cubes = count_red;
                }
                if count_green > green_cubes {
                    green_cubes = count_green;
                }
                if count_blue > blue_cubes {
                    blue_cubes = count_blue;
                }
            }
        }
    }

    (blue_cubes*red_cubes*green_cubes) as i32
}


fn main() -> io::Result<()> {
    let file = File::open("/Users/rodrigoperestrelo/Documents/RepAdventOfCode/AdventOfCode/2023/day02/input.txt")?;
    let reader = io::BufReader::new(file);

    let mut counter = 0;

    for line in reader.lines() {
        counter += treat_line(&line?);
    }
    println!("{}", counter);

    Ok(())
}
