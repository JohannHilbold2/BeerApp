package com.johann.beers.repositories

class TestConnectionService : ConnectionService {
    override fun isConnectedToInternet(): Boolean = true
}