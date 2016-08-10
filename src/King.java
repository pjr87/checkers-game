public class King extends Piece
{
    Direction[] get_move_directions()
    {
       Direction[] directions = {Direction.UpRight, Direction.UpLeft, Direction.DownLeft, Direction.DownRight};
       return directions;
    }
}

