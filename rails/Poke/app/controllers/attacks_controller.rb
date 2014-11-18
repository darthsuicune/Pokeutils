class AttacksController < ApplicationController
	before_action :set_attack, only: [:show, :edit, :update, :destroy]
	
	def index
		
	end
	
	def show
	end
	
	def edit
	end
	
	def update
	end
	
	def destroy
	end
	
	private
		def set_attack
			@attack = Attack.find(params[:id])
		end
end
