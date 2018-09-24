package GameStudio.Service.rating;

import java.util.List;

import GameStudio.Entity.Rating;

public interface RatingService {

	//boolean addRating(Rating rating);
	void changeRating(Rating rating);
	public double getAVGRating(String gameName);
	List<Rating> getAllRatingOfUser(String userName);
	void deleteRating(Long id);
}
