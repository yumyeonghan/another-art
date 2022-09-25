<template>
  <div>
    
  </div>
</template>

<script>
export default {
    name: 'auctionWorkPurchase',
    data() {
      return {
        art: {
                auctionArtBidCount: 5,
                auctionArt: {
                    auctionId: 1,
                    highestBidUserId: 2,
                    highestBidUserNickname: "user2-Nickname",
                    highestBidUserSchoolName: "경기대학교",
                    highestBidPrice: 40000,
                    auctionStartDate: "2022-09-08 00:00:00",
                    auctionEndDate: "2022-09-10 00:00:00",
                    artId: 1,
                    artName: "art1",
                    artDescription: "이 작품은 작품입니다 - 1",
                    artInitPrice: 10000,
                    artRegisterDate: "2022-07-10 09:00:00",
                    artStorageNam: "73eff5de6b.png",
                    artOwnerId: 1,
                    artOwnerNickname: "user1-Nickname",
                    artOwnerSchoolName: "경기대학교"
                },
                artHashtagList: [
                    "반려견",
                    "차",
                    "건강",
                    "포메라니안"
                ]
            },
      }
    }
}
</script>

<style>

</style>