require 'test_helper'

class BroadcastsControllerTest < ActionController::TestCase
  setup do
    @broadcast = broadcasts(:one)
    @feed = feeds(:one)
  end

  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:broadcasts)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create broadcast" do
    assert_difference('Broadcast.count') do
      post :create, broadcast: { content: @broadcast.content, user_id: @broadcast.user_id }, feeds: {id: @feed.id, name: @feed.name}
    end

    assert_redirected_to broadcasts_url(page: 1)
  end

  test "should show broadcast" do
    get :show, id: @broadcast
    assert_response :success
  end

  test "should destroy broadcast" do
    assert_difference('Broadcast.count', -1) do
      delete :destroy, id: @broadcast
    end

    assert_redirected_to broadcasts_path
  end
end
